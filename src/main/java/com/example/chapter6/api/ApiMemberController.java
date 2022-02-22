package com.example.chapter6.api;

import com.example.chapter6.Util.ExceptionMessage;
import com.example.chapter6.Util.Util;
import com.example.chapter6.event.OnLogoutSuccessEvent;
import com.example.chapter6.exception.BadRequestException;
import com.example.chapter6.exception.InsertFailException;
import com.example.chapter6.exception.ResourceAlreadyInUseException;
import com.example.chapter6.exception.UserNotFoundException;
import com.example.chapter6.jwt.AuthService;
import com.example.chapter6.model.MemberVO;
import com.example.chapter6.payload.request.LoginRequest;
import com.example.chapter6.payload.response.ApiResponse;
import com.example.chapter6.payload.response.JwtAuthenticationResponse;
import com.example.chapter6.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class ApiMemberController {
    private static final Logger logger = LoggerFactory.getLogger(ApiMemberController.class);

    private final MemberService memberService;
    private final AuthService authService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/logout")
    public ApiResponse logout(HttpServletRequest request){

        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.replace("Bearer ","");
        // 토큰 검증...
        String res = authService.getUserIdFromJWT(accessToken);
        //test id
        OnLogoutSuccessEvent event = new OnLogoutSuccessEvent(res, accessToken);
        applicationEventPublisher.publishEvent(event);

        return new ApiResponse(true,"완료");
    }


    /**
     * 로그인 처리
     * @param loginRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity loginProcess(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        MemberVO memberVO = new MemberVO();
        memberVO.setUserId(loginRequest.getUserId());
        memberVO.setPassword(loginRequest.getPassword());

        JwtAuthenticationResponse result = memberService.loginProcess(memberVO);

        return ResponseEntity.ok(result);
    }

    /**
     * 토큰 갱신
     * @param refreshToken
     * @return
     */
    @GetMapping("/regenToken")
    @ApiOperation(value="토큰 갱신",notes="갱신할때 리프레시 토큰을 파라미터로 넣음")
    @ApiImplicitParam(name = "refreshToken" , value="리프레시 토큰 값",required = true)
    public ResponseEntity regenToken(@RequestParam(value = "refreshToken", defaultValue = "") String refreshToken){

        if(refreshToken.equals("")){
            logger.info("토큰갱신실패");
            throw new BadRequestException("리프레시 토큰이 없습니다.");
        } else{
            JwtAuthenticationResponse response = authService.regenToken(refreshToken);
            logger.info("토큰갱신성공");
            return ResponseEntity.ok(response);
        }
    }

    @ApiIgnore
    @GetMapping("/apiTest")
    public ApiResponse loginProcess(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ","");
        logger.info("토큰으로 반환된 ID - {}",authService.getUserIdFromJWT(token));
        logger.info("토큰으로 반환된 만료일 - {}",authService.getTokenExpiryFromJWT(token));
        return new ApiResponse(true,"완료");
    }

    /**
     * 아이디 중복 여부 체크
     * @param userId
     * @return
     */
    @GetMapping("/exist/id/{userId}")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200,message = "정상적으로 아이디가 조회되었을때"),
            @io.swagger.annotations.ApiResponse(code=409,message="아이디 중복")
    })
    public ApiResponse existId(@PathVariable String userId) throws Exception {
        if(userId.equals("")) throw new BadRequestException(ExceptionMessage.EMPTY_USER_ID);

        Boolean result= memberService.duplicateId(userId);

        //true로 리턴되면 아이디가 선점되어 있다.
        if(result) throw new ResourceAlreadyInUseException("입력하신 아이디", "userId", userId);

        return new ApiResponse(true,ExceptionMessage.USE_ID_AVAILABLE);
    }

    /**
     * 이메일 중복 여부 체크
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping("/exist/email/{email}")
    public ApiResponse existEmail(@PathVariable String email) throws Exception {
        if(email.equals("")) throw new BadRequestException(ExceptionMessage.EMPTY_USER_ID);

        Boolean result= memberService.duplicateEmail(email);

        //true로 리턴되면 이메일이 선점되어 있다.
        if(result) throw new ResourceAlreadyInUseException("입력하신 이메일", "email", email);

        return new ApiResponse(true,ExceptionMessage.USE_EMAIL_AVAILABLE);

    }

    /**
     * 아이디 찾기
     * @param name
     * @param email
     * @return
     */
    @GetMapping("/find/id/{name}/{email}")
    public ApiResponse findId(
            @PathVariable String name,
            @PathVariable String email
    ){
        if(!name.equals("") && !email.equals("")){
            MemberVO memberVO = new MemberVO();
            memberVO.setName(name);
            memberVO.setEmail(email);
            try{
                String id = memberService.findUserId(memberVO);
                if(id==null){
                    //찾는 id가 없습니다.
                    throw new UserNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID);
                }
                int idLength = id.length();
                id = id.substring(0,idLength-2);

                id+="**";
                return new ApiResponse(true,name + "님이 찾으시는 ID는 "+id+"입니다.");
            } catch(Exception e){
                throw new UserNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID);
            }

        }
        throw new BadRequestException(ExceptionMessage.EMPTY_INFO);
    }

    /**
     * 비밀번호 찾기
     * @param name
     * @param email
     * @param userId
     * @return
     */
    @GetMapping("/find/password/{name}/{email}/{userId}")
    public ApiResponse findPw(
            @PathVariable String name,
            @PathVariable String email,
            @PathVariable String userId
    ){
        if(!name.equals("") && !email.equals("")&& !userId.equals("")){
            MemberVO memberVO = new MemberVO();
            memberVO.setName(name);
            memberVO.setEmail(email);
            memberVO.setUserId(userId);
            try{
                String id = memberService.findPassword(memberVO);
                if(id==null){
                    // 계졍 없음
                    throw new UserNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID);
                }else{
                    //계정 존재 시 비번을 임의로 변경
                    String pw = Util.generateRandomString(10);
                    //변경된 비번 로그찍기
                    logger.info("pw -{}",pw);
                    memberVO.setPassword(pw);
                    memberService.updatePassword(memberVO);
                    return new ApiResponse(true, "변경된 비밀번호는"+pw+"입니다.");
                }
            } catch(Exception e){
                throw new UserNotFoundException(ExceptionMessage.NOT_FOUND_USER_ID);
            }

        }
        throw new BadRequestException(ExceptionMessage.EMPTY_INFO);
    }

    /**
     * 회원 가입 처리
     * @param memberVO
     * @param errors
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity memberJoin(@RequestBody @Valid MemberVO memberVO, Errors errors) throws Exception {

        HashMap<String, Object> errorMap = new HashMap<>();

        if (errors.hasErrors()) {
            Map<String, String> validate = memberService.formValidation(errors);

            for (String key : validate.keySet()) {
                errorMap.put(key, validate.get(key));
            }

            return new ResponseEntity(errorMap, HttpStatus.CONFLICT);
        }

        boolean idCheck = memberService.duplicateId(memberVO.getUserId());
        boolean emailCheck =memberService.duplicateEmail(memberVO.getEmail());

        if(!idCheck&&!emailCheck){
            memberService.insertMember(memberVO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true,"가입완료"));
        } else{
            throw new InsertFailException(ExceptionMessage.SAVE_FAIL);
        }

    }
}
