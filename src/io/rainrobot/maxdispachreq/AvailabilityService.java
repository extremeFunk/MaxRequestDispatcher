package io.rainrobot.maxdispachreq;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AvailabilityService {

    private int maxAvailability;
    private Map<Integer, Set<ServerAddress>> avelabiletyServerSetMap;
    private AddressAvelabiletyTracker addressAvelabiletyTracker;

    private void recomputeMaxAvailability() {
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

    public void initializeMap(int max_requests) {
        for(int i = 0; i <= max_requests; i++){
            avelabiletyServerSetMap.put(i, new HashSet<>());
        }
    }

    public void addNewServer(ServerAddress nuServer, int initialAvailability) {
        maxAvailability = initialAvailability;
        getMaxAvailabilitySet().add(nuServer);
    }

    public void incrementAddressAvailability(ServerAddress address) {
        int availability = addressAvelabiletyTracker.get(address);
        avelabiletyServerSetMap.get(availability).remove(address);
        avelabiletyServerSetMap.get(availability + 1).add(address);
        addressAvelabiletyTracker.put(address, availability + 1);
    }
}
