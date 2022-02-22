package com.example.chapter6.service;

import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BoardService {
    HashMap<String,Object> selectBoardVO(SearchHelper searchHelper) throws Exception;

    Optional<BoardVO> selectBoardVOById(int id) throws Exception;

    void updateBoardVO(BoardVO boardVO) throws Exception;

    void deleteById(int id) throws Exception;

    void insertBoardVO(BoardVO boardVO) throws Exception;

    Map<String, String> formValidation(Errors errors);
}
