package com.example.clone_instagram.dto;

import com.example.clone_instagram.entity.Post;
import lombok.Getter;

@Getter
public class PostMypageResponseDto {

    private Long postId;
    private String image;
    private int likePostNum;
    private int commentNum;

    public PostMypageResponseDto(Post post) {
        this.postId = post.getId();
        this.image = post.getImage();
        this.likePostNum = post.getLikePostList().size();
        this.commentNum = post.getCommentList().size();

    }
}
