package io.rainrobot.maxdispachreq;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoadBalanceService {

    private final int MAX_REQUESTS;
    private int maxAvailability;
    private Map<Integer, Set<serverAddress>> avelabiletyServerMap;
    private io.rainrobot.maxdispachreq.serverManeger serverManeger;

    public LoadBalanceService(int max_requests) {
        MAX_REQUESTS = max_requests;
        //initialize map
        for(int i = 0; i <= MAX_REQUESTS; i++){
            avelabiletyServerMap.put(i, new HashSet<>());
        }
    }

    public serverAddress addSession(String token) {
        //get the set of servers with that have the least amount of sessions
        Set<serverAddress> serverSet = avelabiletyServerMap
                                        .get(maxAvailability);
        serverAddress rndPickServer = pickRndServerAndRemap(token, serverSet);

        int setSize = serverSet.size();
        if (setSize == 0) {
            decrementMaxAvailabilety();
            if (maxAvailability == 0) {
                //if no server will be available to handle
                //next request - create new server
                createNewServer();
            }
        }
        return rndPickServer;
    }

    private serverAddress pickRndServerAndRemap(String token, Set<serverAddress> serverSet) {
        serverAddress rndPickServer = serverSet.iterator().next();
        serverManeger.registerSession(token, rndPickServer);
        //register server availability
        serverManeger.setCurrentAvailability(rndPickServer,
                                            maxAvailability - 1);
        serverSet.remove(rndPickServer);
        avelabiletyServerMap.get(maxAvailability - 1).add(rndPickServer);
        return rndPickServer;
    }

    private void decrementMaxAvailabilety() {
        int setSize;
        do{
            //go to the next set
            maxAvailability --;
            setSize = avelabiletyServerMap.get(maxAvailability).size();
        } while (setSize == 0);
    }


    private void createNewServer() {
        serverAddress nuServer = serverManeger.start();
        int availability = MAX_REQUESTS - 1;
        maxAvailability = availability;
        Set<serverAddress> servers = avelabiletyServerMap
                                    .get(availability);
        servers.add(nuServer);
    }

    public void removeSession(String token, serverAddress addres) {
        serverManeger.unRegisterSession(token, addres);
        //server will be able to handle one more session
        int availability = serverManeger.getCurrentAvelabilety(addres);
        avelabiletyServerMap.get(availability).remove(addres);
        avelabiletyServerMap.get(availability + 1).add(addres);
        serverManeger.setCurrentAvailability(addres,availability + 1);
    }
}
