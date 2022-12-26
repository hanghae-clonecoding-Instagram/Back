package com.example.clone_instagram.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequestDto {

    @NotNull(message = "댓글을 입력해주세요.")
    private String comment;

}
