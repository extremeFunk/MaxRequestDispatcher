package io.rainrobot.maxdispachreq;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoadBalanceService {

    private final int MAX_REQUESTS;
    private int maxAvailability;
    private Map<Integer, Set<ServerAddress>> avelabiletyServerMap;
    private ServerManager serverManeger;
    private ServerPicker picker;

    public LoadBalanceService(int max_requests) {
        MAX_REQUESTS = max_requests;
        //initialize map
        for(int i = 0; i <= MAX_REQUESTS; i++){
            avelabiletyServerMap.put(i, new HashSet<>());
        }
    }

    public ServerAddress createSession(String token) {
        //get the set of servers with that have the least amount of sessions
        Set<ServerAddress> serverSet = avelabiletyServerMap
                                        .get(maxAvailability);
        ServerAddress rndPickServer = picker.pickAndRemap(token, serverSet);
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
    


    private void decrementMaxAvailabilety() {
        int setSize;
        do{
            //go to the next set
            maxAvailability --;
            setSize = avelabiletyServerMap.get(maxAvailability).size();
        } while (setSize == 0 && maxAvailability != 1);
        maxAvailability --;
    }


    private void createNewServer() {
        ServerAddress nuServer = serverManeger.start();
        int availability = MAX_REQUESTS - 1;
        maxAvailability = availability;
        Set<ServerAddress> servers = avelabiletyServerMap
                                    .get(availability);
        servers.add(nuServer);
    }

    public void removeSession(Session session) {
        String token = session.getToken();
        ServerAddress address = session.getSessionAddress();

        serverManeger.unRegisterSession(token, address);
        //server will be able to handle one more session
        int availability = serverManeger.getCurrentAvelabilety(address);
        avelabiletyServerMap.get(availability).remove(address);
        avelabiletyServerMap.get(availability + 1).add(address);
        serverManeger.setCurrentAvailability(address,availability + 1);
    }
}
