package io.rainrobot.maxdispachreq;

import java.util.Map;

public class Dispatcher {
    private Map<String, Session> tokenSessionMap;
    private LoadBalanceService loadBalancerService;

    //API
    public serverAddress createSession(String token) {
        Session session = tokenSessionMap.get(token);
        if(session != null && !session.isExpired()) {
            return session.getSessionAddress();
        }
        serverAddress addres = loadBalancerService.addSession(token);
        session.setAddress(addres);
        tokenSessionMap.remove(session.getToken());
        tokenSessionMap.put(session.getToken(), session);

        return addres;
    }

    //API
    public void endSession(String token) {
        Session session = tokenSessionMap.get(token);
        if(session != null && !session.isExpired()) {
            serverAddress adders = session.getSessionAddress();
            loadBalancerService.removeSession(token, adders);
            tokenSessionMap.remove(token);
        }
    }
}
