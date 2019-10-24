package io.rainrobot.maxdispachreq;

public class SimpleSession implements Session{
    private String token;
    private ServerAddress address;

    public SimpleSession(Builder b) {
        token = b.token;
        address = b.address;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public ServerAddress getSessionAddress() {
        return address;
    }

    @Override
    public void setAddress(ServerAddress address) {
        this.address = address;
    }

    static class Builder {
        String token;
        ServerAddress address;

        public Builder() {}

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder addres(ServerAddress address) {
            this.address = address;
            return this;
        }

        public SimpleSession build() {
            return new SimpleSession(this);
        }
    }
}
