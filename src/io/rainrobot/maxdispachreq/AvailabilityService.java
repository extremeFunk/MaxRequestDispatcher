package io.rainrobot.maxdispachreq;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AvailabilityService {
    //maxAvailability keep track of the
    //set of servers with the most capacity
    //for adding sessions
    //in case the value is 0 that mean
    //that all the servers cant take any more
    //sessions
    private int maxAvailability;
    private Map<Integer, Set<ServerAddress>> avelabiletyServerSetMap;
    private AddressAvelabiletyTracker addressAvelabiletyTracker;

    private void recomputeMaxAvailability() {
        //if the current set has at least 1 server
        //then the maximum availability is the same
        if (getMaxAvailabilitySet().size() == 0) {
            do{
                maxAvailability --;
            } while (getMaxAvailabilitySet().size() == 0
                                    && maxAvailability != 1);
            maxAvailability --;
        }
    }
    
    private Set<ServerAddress> getMaxAvailabilitySet() {
        return avelabiletyServerSetMap.get(maxAvailability);
    }

    public ServerAddress popFromMaxAvailableSet() {
        ServerAddress popServer = getMaxAvailabilitySet().iterator().next();
        getMaxAvailabilitySet().remove(popServer);
        decrementAddressAvailability(popServer, maxAvailability);
        recomputeMaxAvailability();
        return popServer;
    }

    private void decrementAddressAvailability(ServerAddress popServer, int availability) {
        avelabiletyServerSetMap.get(availability).remove(popServer);
        avelabiletyServerSetMap.get(availability - 1).add(popServer);
        addressAvelabiletyTracker.put(popServer, availability -1);
    }

    public boolean fullCapacity() {
        return maxAvailability == 0;
    }

    public void initializeMap(int max_sessions_per_server) {
        //for each possible number of available session on a server
        // a set is created to hold all the servers with this number
        // of session that can be added (for instance key 5
        // will return the set of all the servers that can add
        // 5 more sessions)
        for(int i = 0; i <= max_sessions_per_server; i++){
            avelabiletyServerSetMap.put(i, new HashSet<>());
        }
    }

    public void addNewServer(ServerAddress nuServer, int initialAvailability) {
        maxAvailability = initialAvailability;
        avelabiletyServerSetMap.get(initialAvailability).add(nuServer);
    }

    public void incrementAddressAvailability(ServerAddress address) {
        int availability = addressAvelabiletyTracker.get(address);
        avelabiletyServerSetMap.get(availability).remove(address);
        avelabiletyServerSetMap.get(availability + 1).add(address);
        addressAvelabiletyTracker.put(address, availability + 1);
    }
}
