package com.example.chapter6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    HandlerInterceptor loginInterceptor;

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/member/login","/api/member/join","/api/member/find/**","/api/member/regenToken"
                );
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/board/**")
                .excludePathPatterns(
                        "/member/**","/webjars/**/**","/swagger-ui.html"
                );
    }
}
