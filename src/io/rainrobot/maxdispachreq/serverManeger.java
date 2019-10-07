package io.rainrobot.maxdispachreq;

public interface serverManeger {
    serverAddress start();

    void registerSession(String token, serverAddress avelabiletyServerMap);

    void unRegisterSession(String token, serverAddress addres);

    void setCurrentAvailability(serverAddress rndAvailableServer, int i);

    int getCurrentAvelabilety(serverAddress addres);
}
