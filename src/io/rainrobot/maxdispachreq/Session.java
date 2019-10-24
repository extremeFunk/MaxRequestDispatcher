package io.rainrobot.maxdispachreq;

public interface Session {
    String getToken();

    boolean isExpired();

    ServerAddress

    getSessionAddress();

    void setAddress(ServerAddress address);
}
