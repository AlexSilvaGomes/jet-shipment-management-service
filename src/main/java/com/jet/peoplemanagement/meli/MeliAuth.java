package com.jet.peoplemanagement.meli;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MeliAuth {

    Map<String, MeliOAuthClient> meliMap = new HashMap<>();

    public MeliOAuthClient getToken(String clientId){
        return meliMap.get(clientId);
    }

    public void putToken(String clientId, MeliOAuthClient meliToken){
        meliMap.put(clientId, meliToken);
    }

    public boolean isExpired(String clientId){
       if(hasToken(clientId)){
           MeliOAuthClient auth = getToken(clientId);
           long current = System.currentTimeMillis();
           return current > auth.getCalculatedExpiration();
       }
       return true;
    }

    public boolean hasToken(String clientId){
        return meliMap.containsKey(clientId);
    }
}
