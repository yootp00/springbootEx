package com.example.chapter6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenValidateException extends RuntimeException{
    public InvalidTokenValidateException(String message) {
        super(message);
    }

    public InvalidTokenValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
