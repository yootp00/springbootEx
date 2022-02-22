package com.example.chapter6.ServiceImpl;

import com.example.chapter6.mapper.BoardMapper;
import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardServiceImplTest {

    @Autowired
    BoardServiceImpl boardService;

    @Autowired
    BoardMapper boardMapper;

    @Test
    @DisplayName(value = "게시물 선택")
    void selectBoardVO() throws Exception {
        SearchHelper searchHelper = new SearchHelper();
        searchHelper.setSrchCode(1000);
        System.out.println(boardService.selectBoardVO(searchHelper));
    }

    @Test
    @DisplayName(value="게시물 조회")
   void selectBoardVOById() throws Exception{
        System.out.println(boardService.selectBoardVOById(1000));
    }


    @Test
    @DisplayName(value="게시물 수정")
    void updateBoardVO() throws Exception {
        BoardVO boardVO = new BoardVO();
        boardVO.setId(2415);
        boardVO.setTitle("test title 변경");
        boardVO.setContent("test content 변경");
        boardService.updateBoardVO(boardVO);
    }

    @Test
    @DisplayName(value="게시물 삭제")
    void deleteById() throws Exception {
        boardService.deleteById(1334);
    }

    @Test
    @DisplayName(value="게시물 생성")
   void insertBoardVO() throws Exception {
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("test title 변경");
        boardVO.setContent("test content 변경");
        boardService.insertBoardVO(boardVO);
    }

    @Test
    void formValidation() {
    }
}