package com.example.chapter6.config;

import com.example.chapter6.jwt.JwtTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    private final JwtTokenValidator jwtTokenValidator;

    public TokenInterceptor(JwtTokenValidator jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        log.info(request.getMethod());

        if(StringUtils.equals(request.getMethod(),"OPTIONS")){
            log.info("OPTIONS 메소드 이다.");
            return true;
        }

        log.info("token before- {}", token);
        token = token.replace("Bearer ","");
        log.info("token after- {}", token);
        //토큰 검증 메서드
        Boolean valid = jwtTokenValidator.validateToken(token);

        if(valid){
            return true;
        } else {
            return false;
        }
    }
}
