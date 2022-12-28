package com.example.clone_instagram.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostFeedResponseDto {

    List<PostMainResponseDto> postFeed = new ArrayList<>();

    public void addPostFeed(PostMainResponseDto responseDto) {
        postFeed.add(responseDto);
    }

}
