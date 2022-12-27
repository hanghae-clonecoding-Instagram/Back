package com.example.clone_instagram.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostRequestDto {

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

}
