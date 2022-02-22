package com.example.chapter6.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private final Boolean success;
    private final String data;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;
    private final String cause;
    private final HashMap<String,Object> map;

    public ApiResponse(Boolean success, String data) {
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.cause=null;
        this.map=null;
    }

    public ApiResponse(Boolean success, String data, String cause) {
        this.success = success;
        this.data = data;
        this.cause = cause;
        this.timestamp = LocalDateTime.now();
        this.map=null;
    }

    public ApiResponse(Boolean success, String data, HashMap<String, Object> map) {
        this.success = success;
        this.cause = null;
        this.map = map;
        this.timestamp = LocalDateTime.now();
        this.data=data;
    }
}
