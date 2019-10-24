package io.rainrobot.maxdispachreq;

import java.util.Map;

public class AddressAvelabiletyTracker {
    private Map<ServerAddress, Integer> addressAvelabilety;

    public void put(ServerAddress address, int avelabilety) {
            addressAvelabilety.put(address, avelabilety);
    }

    public int get(ServerAddress address) {
        return addressAvelabilety.get(address);
    }
}
