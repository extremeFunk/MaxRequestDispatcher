package io.rainrobot.maxdispachreq;

import java.util.Map;

public class AddressService {

    private Map<String, Session> tokenSessionMap;
    private LoadBalanceService loadBalancerService;

    public ServerAddress getAddress(String id) {
        return createSession(id);
    }

    public ServerAddress createSession(String token) {
        Session session = tokenSessionMap.get(token);
        if(session != null && !session.isExpired()) {
            return session.getSessionAddress();
        }
        ServerAddress addres = loadBalancerService.addSession(token);
        session.setAddress(addres);
        tokenSessionMap.remove(session.getToken());
        tokenSessionMap.put(session.getToken(), session);

        return addres;
    }

    public void endSession(String token) {
        Session session = tokenSessionMap.get(token);
        if(session != null && !session.isExpired()) {
            ServerAddress adders = session.getSessionAddress();
            loadBalancerService.removeSession(token, adders);
            tokenSessionMap.remove(token);
        }
    }
}
