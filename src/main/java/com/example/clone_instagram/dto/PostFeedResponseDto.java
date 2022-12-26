package com.example.clone_instagram.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostFeedResponseDto {

    List<PostDetailResponseDto> postFeed = new ArrayList<>();

    public void addPostFeed(PostDetailResponseDto responseDto) {
        postFeed.add(responseDto);
    }

}
