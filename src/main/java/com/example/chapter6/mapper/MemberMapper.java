package com.example.chapter6.mapper;

import com.example.chapter6.model.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    Boolean duplicateId(String id) throws Exception;

    Boolean duplicateEmail(String email) throws Exception;

    void insertMember(MemberVO memberVO) throws Exception;

    Optional<MemberVO> loginProcess(MemberVO memberVO) throws Exception;

    String findUserId(MemberVO memberVO) throws Exception;

    String findPassword(MemberVO memberVO) throws Exception;

    void updatePassword(MemberVO memberVO)throws Exception;

}

