package com.example.chapter6.ServiceImpl;

import com.example.chapter6.mapper.BoardMapper;
import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import com.example.chapter6.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
public class BoardServiceImpl implements BoardService{

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

    private BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {

        this.boardMapper = boardMapper;
    }

    /**
     * 게시물 목록
     * @return
     */
    @Override
    public HashMap<String,Object> selectBoardVO(SearchHelper searchHelper) throws Exception {

        HashMap<String,Object> resultMap = new HashMap<>();

        int totalCnt=boardMapper.countBoardVO(searchHelper);

        int code = searchHelper.getSrchCode();
        String srchType = searchHelper.getSrchType();
        String srchKeyword = searchHelper.getSrchKeyword();

        searchHelper = new SearchHelper(totalCnt,searchHelper.getPage());

        searchHelper.setSrchCode(code);
        searchHelper.setSrchType(srchType);
        searchHelper.setSrchKeyword(srchKeyword);

        List<BoardVO> list = boardMapper.selectBoardVO(searchHelper);
        resultMap.put("list",list);
        resultMap.put("searchHelper",searchHelper);

        return resultMap;
    }

    /**
     * 게시물 조회
     * @param id
     * @return
     */
    @Override
    public Optional<BoardVO> selectBoardVOById(int id) throws Exception {
            boardMapper.updateCount(id);
            return boardMapper.selectBoardVOById(id);

    }

    /**
     * 게시물 수정
     * @param boardVO
     */
    @Override
    public void updateBoardVO(BoardVO boardVO) throws Exception {
        Optional<BoardVO> isExist = boardMapper.selectBoardVOById(boardVO.getId());
        if(isExist.isPresent()){
            boardMapper.updateBoardVO(boardVO);
        }else{
            throw new Exception();
        }

    }

    /**
     * 게시물 삭제
     * @param id
     */
    @Override
    public void deleteById(int id) throws Exception {
        Optional<BoardVO> isExist = boardMapper.selectBoardVOById(id);
        if(isExist.isPresent()){
            boardMapper.deleteById(id);
        }else{
            throw new Exception();
        }
    }


    /**
     * 게시물 저장
     * @param boardVO
     */
    @Override
    public void insertBoardVO(BoardVO boardVO) throws Exception {
        boardMapper.insertBoardVO(boardVO);
    }

    @Override
    public Map<String, String> formValidation(Errors errors){
        Map<String,String> result = new HashMap<>();
        for(FieldError error: errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s",error.getField());
            result.put(validKeyName,error.getDefaultMessage());
        }
        return result;
    }
}
