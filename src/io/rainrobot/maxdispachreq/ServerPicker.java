package io.rainrobot.maxdispachreq;

import java.util.Set;

public class ServerPicker {

    public ServerAddress pickAndRemap(String token, Set<ServerAddress> serverSet) {
        ServerAddress rndPickServer = serverSet.iterator().next();
        serverManeger.registerSession(token, rndPickServer);
        //register server availability
        serverManeger.setCurrentAvailability(rndPickServer,
                maxAvailability - 1);
        serverSet.remove(rndPickServer);
        avelabiletyServerMap.get(maxAvailability - 1).add(rndPickServer);
        return rndPickServer;
    }
}
