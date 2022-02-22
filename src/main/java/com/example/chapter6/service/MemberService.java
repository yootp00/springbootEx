package com.example.chapter6.service;

import com.example.chapter6.model.MemberVO;
import com.example.chapter6.payload.response.JwtAuthenticationResponse;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

public interface MemberService {

    Boolean duplicateId(String id) throws Exception;

    Boolean duplicateEmail(String email) throws Exception;

    void insertMember(MemberVO memberVO) throws Exception;

    Boolean loginProcess(MemberVO memberVO, HttpServletRequest request) throws Exception;

    JwtAuthenticationResponse loginProcess(MemberVO memberVO) throws Exception;

    String findUserId(MemberVO memberVO) throws Exception;

    String findPassword(MemberVO memberVO) throws Exception;

    void updatePassword(MemberVO memberVO) throws Exception;

    Map<String, String> formValidation(Errors errors);

}
