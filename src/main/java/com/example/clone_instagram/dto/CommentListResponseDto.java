package com.example.clone_instagram.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentListResponseDto {

    List<CommentResponseDto> commentList = new ArrayList<>();

    public void addCommentList(CommentResponseDto responseDto) {
        commentList.add(responseDto);
    }

}
