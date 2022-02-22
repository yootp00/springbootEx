package com.example.chapter6.api;

import com.example.chapter6.Util.ExceptionMessage;
import com.example.chapter6.exception.BadRequestException;
import com.example.chapter6.exception.InsertFailException;
import com.example.chapter6.file.UploadFileService;
import com.example.chapter6.jwt.AuthService;
import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.SearchHelper;
import com.example.chapter6.model.UploadFileVO;
import com.example.chapter6.payload.response.ApiResponse;
import com.example.chapter6.service.BoardService;
import com.example.chapter6.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/board")
public class ApiBoardController {
    private static final Logger logger = LoggerFactory.getLogger(ApiBoardController.class);

    private final BoardService boardService;
    private final MemberService memberService;
    private final AuthService authService;
    private final UploadFileService uploadFileService;


    /**
     * 게시물 목록
     * @param searchHelper
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "게시물 목록",notes = "게시물 목록을 불러옵니다. page 파라미터를 최소 1이상으로 설정해서 실행하세요.")
    public ResponseEntity boardList(
            @RequestBody SearchHelper searchHelper
    ) throws Exception {
        HashMap<String,Object> result = boardService.selectBoardVO(searchHelper);

        return new ResponseEntity(result,HttpStatus.OK);
    }
    /**
     * 게시물 조회
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResponseEntity boardView(
            @PathVariable int id
    ) throws Exception {
        Optional<BoardVO> boardVO = boardService.selectBoardVOById(id);
        if(boardVO.isPresent()) {
            return new ResponseEntity(boardVO,HttpStatus.OK);
        } else{
            throw new BadRequestException(ExceptionMessage.ARTICLE_NOT_FOUND);
        }

    }

    /**
     * 게시물 저장
     * @param boardVO
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity boardSave(
            @RequestBody @Valid BoardVO boardVO, Errors errors,
            HttpServletRequest request
    ) throws Exception {

        String token =request.getHeader("Authorization");
        token = token.replace("Bearer ","");

        String userId = authService.getUserIdFromJWT(token);

        if(!userId.equals("")){
            boardVO.setRegId(userId);
        } else{
            throw new InsertFailException(ExceptionMessage.SAVE_FAIL);
        }

        HashMap<String,Object> errorMap = new HashMap<>();

        if (errors.hasErrors()) {
            Map<String, String> validate = boardService.formValidation(errors);

            for(String key : validate.keySet()){
                errorMap.put(key, validate.get(key));
            }

            return new ResponseEntity(errorMap,HttpStatus.CONFLICT);
        }

        try{
            boardService.insertBoardVO(boardVO);
        }catch (Exception e){
            throw new InsertFailException(ExceptionMessage.SAVE_FAIL);
        }

        return new ResponseEntity("OK",HttpStatus.OK);
    }

    /**
     * 게시물 수정
     * @param boardVO
     * @return
     * @throws Exception
     */
    @PutMapping("/update")
    public ResponseEntity boardUpdate(
            @RequestBody BoardVO boardVO,
            HttpServletRequest request
    ) throws Exception {

        String token =request.getHeader("Authorization");
        token = token.replace("Bearer ","");

        String userId = authService.getUserIdFromJWT(token);

        if(!userId.equals("")){
            boardVO.setRegId(userId);
        } else{
            throw new BadRequestException(ExceptionMessage.NOT_FOUND_ARTICLE);
        }

        int id = boardVO.getId();
        Optional<BoardVO> exist = boardService.selectBoardVOById(id);

        if(exist.isPresent()){
            boardService.updateBoardVO(boardVO);
            return new ResponseEntity("OK",HttpStatus.OK);
        } else{
            throw new BadRequestException(ExceptionMessage.NOT_FOUND_ARTICLE);
        }
    }

    /**
     * 게시물 삭제
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse boardDelete(
            @PathVariable int id
    ) throws Exception {

        Optional<BoardVO> boardVO = boardService.selectBoardVOById(id);
        if(boardVO.isPresent()){
            try{
                boardService.deleteById(id);
            }catch(Exception e){
                throw new InsertFailException(ExceptionMessage.DELETE_FAIL);
            }
        } else{
            throw new InsertFailException(ExceptionMessage.NOT_FOUND_ARTICLE);
        }

        return new ApiResponse(true,ExceptionMessage.DELETE_SUCCESS);
    }

    /**
     * ajax 파일 저장
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/ajaxFileUpload")
    public ResponseEntity ajaxFileUpload(
            @RequestParam("file")MultipartFile multipartFile
            ) throws Exception{
        UploadFileVO uploadFileVO=uploadFileService.saveFile(multipartFile);
        String savedFileId = String.valueOf(uploadFileVO.getId());
        return ResponseEntity.ok(new ApiResponse(true,savedFileId));
    }

}
