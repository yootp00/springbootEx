package com.example.chapter6.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class OnLogoutSuccessEvent extends ApplicationEvent {

    private final String userId;
    private final String accessToken;
    private final LocalDateTime eventTime;

    public OnLogoutSuccessEvent(String userId, String accessToken) {
        super(userId);
        this.userId = userId;
        this.accessToken = accessToken;
        this.eventTime = LocalDateTime.now();
    }
}
