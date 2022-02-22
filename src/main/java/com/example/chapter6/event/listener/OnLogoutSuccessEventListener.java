package com.example.chapter6.event.listener;

import com.example.chapter6.event.OnLogoutSuccessEvent;
import com.example.chapter6.jwt.LoggedOutJwtTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnLogoutSuccessEventListener {

    private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;

    public OnLogoutSuccessEventListener(LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
        this.loggedOutJwtTokenCache = loggedOutJwtTokenCache;
    }

    @EventListener
    @Async
    public void eventHandleMethod(OnLogoutSuccessEvent event){
        if(event != null){
            String userId = event.getUserId();
            log.info(String.format("로그아웃 이벤트 [%s]",userId));
            loggedOutJwtTokenCache.markLogoutEventForToken(event);
        }
    }
}
