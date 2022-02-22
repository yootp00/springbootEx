package com.example.chapter6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyInUseException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final String fieldValue;

    public ResourceAlreadyInUseException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s는(은) 이미 사용중입니다. [%s]:[%s]",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
