package com.example.chapter6.config;

import com.example.chapter6.jwt.JwtTokenValidator;
import com.example.chapter6.model.MemberVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");

        if (memberVO != null) {
            if(memberVO.getLevel()==10){
                return true;
            } else {
                return true;
            }
        } else {
            response.sendRedirect("/member/login");
            return false;
        }
    }
}
