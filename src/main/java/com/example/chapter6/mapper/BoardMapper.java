package com.example.chapter6.mapper;

import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    List<BoardVO> selectBoardVO(SearchHelper searchHelper) throws Exception;

    int countBoardVO(SearchHelper searchHelper) throws Exception;

    Optional<BoardVO> selectBoardVOById(int id) throws Exception;

    void updateBoardVO(BoardVO boardVO) throws Exception;

    void updateCount(int id) throws Exception;

    void deleteById(int id) throws Exception;

    void insertBoardVO(BoardVO boardVO) throws Exception;

    Map<String, String> formValidation(Errors errors);
}
