package com.example.chapter6.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {

    private int id;

    @NotBlank(message = "아이디를 입력하세요.")
    private String userId;

    @NotBlank(message = "이메일을 입력하세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "비밀번호는 영문, 숫자, 특수기호가 최소 1개 이상 포함되어야합니다.")
    private String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 2, max = 10, message = "이름은 최소 2자 이상 10자 이하로 입력하세요.")
    private String name;

    private int level;
    private String status;
    private int cert;
    private LocalDateTime regDate;
    private LocalDateTime dropDate;

}
