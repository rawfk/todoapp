package com.example.todoapp.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupDto {

    @NotBlank(message = "닉네임을 입력하세요.")
    @Pattern(regexp ="^[a-zA-Z0-9]{3,}$", message = "아이디를 확인해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호를 4자 이상 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호(확인)를 입력하세요.")
    @Size(min = 4, message = "비밀번호(확인)를 4자 이상 입력해주세요.")
    private String passwordChk;
}
