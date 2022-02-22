package com.example.chapter6.mapper;

import com.example.chapter6.exception.BadRequestException;
import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.MemberVO;
import com.example.chapter6.model.SearchHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;

    @Test
    void 게시물_목록_호출() throws Exception {
        BoardVO boardVO = new BoardVO();
        boardVO.setCode(1000);
        boardVO.setTitle("test ins");
        boardVO.setContent("내용");
        boardVO.setRegId("test");
        boardMapper.insertBoardVO(boardVO);
    }

}