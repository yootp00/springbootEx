package com.example.chapter6.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class RefreshTokenExpirationException extends RuntimeException{

    private final String token;
    private final String message;

    public RefreshTokenExpirationException(String message, String token) {
        super(String.format("토큰 갱신 실패 [%s] : [%s]",token,message));
        this.token = token;
        this.message = message;
    }
}
