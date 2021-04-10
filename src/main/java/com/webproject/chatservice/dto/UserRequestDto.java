package com.webproject.chatservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotNull
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "3~20자리의 숫자 또는 문자만 가능합니다.")
    private String username;

    @NotNull
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotNull
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;
}
