package com.example.clone_instagram.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "email을 입력해주세요.")
    @Email(message = "email형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "password를 입력해주세요.")
    private String password;


}
