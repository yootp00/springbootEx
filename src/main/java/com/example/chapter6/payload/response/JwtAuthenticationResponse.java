package com.example.chapter6.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JwtAuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType="Bearer ";
    private Long expiryDuration;

    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = "Bearer ";
        this.expiryDuration = expiryDuration;
    }
}
