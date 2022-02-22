package com.example.chapter6.mapper;

import com.example.chapter6.model.RefreshTokenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {

    Optional<RefreshTokenVO> selectByToken(String token);

    void insertToken(RefreshTokenVO refreshTokenVO);

    void deleteTokenById(int id);

    void updateTokenCount(RefreshTokenVO refreshTokenVO);

    boolean existMemberId(int id);

}
