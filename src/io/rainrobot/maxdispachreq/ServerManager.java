package io.rainrobot.maxdispachreq;

public interface ServerManager {
    ServerAddress start();

    void registerSession(String token, ServerAddress avelabiletyServerMap);

    void unRegisterSession(String token, ServerAddress addres);

}
