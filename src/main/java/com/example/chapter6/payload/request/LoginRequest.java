package com.example.chapter6.payload.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequest {

    @NonNull
    private String userId;

    @NonNull
    private String password;

    public LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
