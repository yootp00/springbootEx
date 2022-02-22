package com.example.chapter6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsertFailException extends RuntimeException{
    public InsertFailException(String message) {
        super(message);
    }

    public InsertFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
