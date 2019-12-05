package io.rainrobot.maxdispachreq;

public interface Session {

    void setToken(String token);

    String getToken();

    boolean isExpired();

    ServerAddress getSessionAddress();

    void setAddress(ServerAddress address);

}
