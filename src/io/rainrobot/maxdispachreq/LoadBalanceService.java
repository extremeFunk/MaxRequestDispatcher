package io.rainrobot.maxdispachreq;

public class LoadBalanceService {

    private final int MAX_REQUESTS;
    private AvailabilityService avelabiletyService;
    private ServerManager serverManeger;


    public LoadBalanceService(int max_requests) {
        MAX_REQUESTS = max_requests;
        //initialize map
        avelabiletyService.initializeMap(max_requests);
    }

    public ServerAddress getNextAddress(String token) {
        //get the set of servers with that have the least amount of sessions
        ServerAddress address = avelabiletyService.popFromMaxAvailableSet();
        serverManeger.registerSession(token, address);
        if (avelabiletyService.fullCapacity()) {
            //if no server will be available to handle
            //next request - create new server
            createNewServer();
        }
        return address;
    }


    private void createNewServer() {
        ServerAddress nuServer = serverManeger.start();
        avelabiletyService.addNewServer(nuServer, MAX_REQUESTS - 1);
    }

    public void removeSession(Session session) {
        String token = session.getToken();
        ServerAddress address = session.getSessionAddress();

        serverManeger.unRegisterSession(token, address);
        //server will be able to handle one more session
        avelabiletyService.incrementAddressAvailability(address);
    }
}
