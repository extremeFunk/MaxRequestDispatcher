package io.rainrobot.maxdispachreq;

import java.util.Map;

public class SessionCache {
    private Map<String, Session> tokenSessionMap;


    public boolean exist(String token) {
        Session session = tokenSessionMap.get(token);
        //if exist & not expired
        if(session != null && !session.isExpired()) {
            return true;
        }
        //if expired
        else if (session != null){
            tokenSessionMap.remove(token);
            return false;
        }
        //if not exist
        else return false;
    }

    public Session get(String token) {
        return tokenSessionMap.get(token);
    }

    public void add(Session s) {
        tokenSessionMap.put(s.getToken(), s);
    }

    public void remove(String token) {
        tokenSessionMap.remove(token);
    }
}
