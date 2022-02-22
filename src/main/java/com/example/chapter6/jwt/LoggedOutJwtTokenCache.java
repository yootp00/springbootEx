package com.example.chapter6.jwt;

import com.example.chapter6.event.OnLogoutSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LoggedOutJwtTokenCache {

    private final ExpiringMap<String, OnLogoutSuccessEvent> tokenEventMap;
    private final JwtTokenProvider jwtTokenProvider;

    public LoggedOutJwtTokenCache(@Value("${app.jwt.logoutToken.maxSize}") int maxSize, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(maxSize)
                .build();
    }

    public void markLogoutEventForToken(OnLogoutSuccessEvent event){
        String token = event.getAccessToken();
        if(!tokenEventMap.containsKey(token)){

            Date tokenExpiryDate = jwtTokenProvider.getTokenExpiryFromJWT(token);
            Long secondAtExpiry = tokenExpiryDate.toInstant().getEpochSecond();
            Long secondAtLogout = Instant.now().getEpochSecond();
            Long ttlForToken = Math.max(0,secondAtExpiry-secondAtLogout);



            tokenEventMap.put(token,event,ttlForToken, TimeUnit.SECONDS);
        }
    }

    public OnLogoutSuccessEvent getLogoutEventForToken(String token){
        return tokenEventMap.get(token);

    }

}
