package com.example.chapter6.jwt;

import com.example.chapter6.event.OnLogoutSuccessEvent;
import com.example.chapter6.exception.BadRequestException;
import com.example.chapter6.exception.InvalidTokenValidateException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class JwtTokenValidator {

    private final String jwtSecret;
    private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;

    public JwtTokenValidator(@Value("${app.jwt.secret}") String jwtSecret,LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
        this.jwtSecret = jwtSecret;
        this.loggedOutJwtTokenCache = loggedOutJwtTokenCache;
    }

    public boolean validateToken(String authToken){

        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch(SignatureException ex){
            log.info("서명이 맞지 않습니다.");
            throw new InvalidTokenValidateException("서명이 맞지 않습니다.");
        } catch(MalformedJwtException ex){
            log.info("토큰 형식이 맞지 않습니다.");
            throw new InvalidTokenValidateException("토큰 형식이 맞지 않습니다.");
        } catch(UnsupportedJwtException ex) {
            log.info("지원 불가 토큰");
            throw new InvalidTokenValidateException("지원되지 않는 토큰입니다.");

        } catch(ExpiredJwtException ex){
            log.info("유효기간 만료");
            throw new InvalidTokenValidateException("토큰의 유효기간이 만료되었습니다.");
        } catch(IllegalArgumentException ex){
            log.info("Claims 정보가 없습니다.");
            throw new InvalidTokenValidateException("Claims 정보가 없습니다.");
        }

        validTokenLogout(authToken);

        return true;
    }
    private void validTokenLogout(String authToken){
        OnLogoutSuccessEvent event = loggedOutJwtTokenCache.getLogoutEventForToken(authToken);
        if(event != null){
            String userId = event.getUserId();
            LocalDateTime localDateTime = event.getEventTime();
            log.info(String.format("",userId,localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            throw new BadRequestException("로그아웃된 토큰입니다.");
        }
    }
}
