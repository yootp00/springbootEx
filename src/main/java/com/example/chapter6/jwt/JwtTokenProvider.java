package com.example.chapter6.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret;
    private final long jwtExpirationMs;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.expiration}") long jwtExpirationMs
    ) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /**
     * 토큰 생성
     * @param userId
     * @return
     */
    public String generateToken(String userId){

        Instant expireDate = Instant.now().plusMillis(jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expireDate))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    /**
     * 토큰으로 사용자 아이디 반환
     * @param token
     * @return
     */
    public String getuserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * 토큰으로 만료일 반환
     * @param token
     * @return
     */
    public Date getTokenExpiryFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }
}
