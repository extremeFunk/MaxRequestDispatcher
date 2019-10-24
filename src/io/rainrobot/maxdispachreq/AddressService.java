package io.rainrobot.maxdispachreq;

public class AddressService {

    private SessionCache cache;
    private LoadBalanceService loadBalance;

    public ServerAddress getAddress(String token) {
        if(cache.exist(token)) {
            return cache.get(token).getSessionAddress();
        }
        else {
            ServerAddress adders = loadBalance.createSession(token);
            cache.add(buildSession(token, adders));
            return adders;
        }
    }

    private SimpleSession buildSession(String token, ServerAddress adders) {
        return new SimpleSession.Builder().token(token).addres(adders).build();
    }


    public void endSession(String token) {
        if(cache.exist(token)) {
            loadBalance.removeSession(cache.get(token));
            cache.remove(token);
        }
    }
}
