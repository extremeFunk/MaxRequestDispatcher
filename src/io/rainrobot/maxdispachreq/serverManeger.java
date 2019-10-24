package io.rainrobot.maxdispachreq;

public interface ServerManeger {
    ServerAddress start();

    void registerSession(String token, ServerAddress avelabiletyServerMap);

    void unRegisterSession(String token, ServerAddress addres);

    void setCurrentAvailability(ServerAddress rndAvailableServer, int i);

    int getCurrentAvelabilety(ServerAddress addres);
}
