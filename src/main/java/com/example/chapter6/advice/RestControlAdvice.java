package com.example.chapter6.advice;

import com.example.chapter6.exception.*;
import com.example.chapter6.payload.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControlAdvice {
    private static final Logger logger = LoggerFactory.getLogger(RestControlAdvice.class);

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleBadRequestException(BadRequestException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }

    @ExceptionHandler(value = InsertFailException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiResponse handleInsertFailException(InsertFailException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }
    @ExceptionHandler(value= ResourceAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse handleResourceAlreadyInUseException(ResourceAlreadyInUseException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }

    @ExceptionHandler(value = InvalidTokenValidateException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiResponse handleInvalidTokenValidateException(InvalidTokenValidateException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }

    @ExceptionHandler(value=UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleUserNotFoundException(UserNotFoundException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }
    @ExceptionHandler(value = RefreshTokenExpirationException.class)
    public ApiResponse handleRefreshTokenExpirationException(RefreshTokenExpirationException ex){
        return new ApiResponse(false,ex.getMessage(),ex.getClass().getName());
    }
}
