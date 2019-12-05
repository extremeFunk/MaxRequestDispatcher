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
        //TODO remove popServer from getMaxAvailabilitySet()
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
    //TODO rename to max_session
    public void initializeMap(int max_requests) {
        for(int i = 0; i <= max_requests; i++){
            avelabiletyServerSetMap.put(i, new HashSet<>());
        }
    }

    public void addNewServer(ServerAddress nuServer, int initialAvailability) {
        maxAvailability = initialAvailability;
        //TODO the new server should
        // go to availabilityServerSetMap.get(initialAvailability)
        getMaxAvailabilitySet().add(nuServer);
    }

    public void incrementAddressAvailability(ServerAddress address) {
        int availability = addressAvelabiletyTracker.get(address);
        avelabiletyServerSetMap.get(availability).remove(address);
        avelabiletyServerSetMap.get(availability + 1).add(address);
        addressAvelabiletyTracker.put(address, availability + 1);
    }
}
