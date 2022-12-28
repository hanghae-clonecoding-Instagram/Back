package com.example.clone_instagram.dto;

import com.example.clone_instagram.entity.Comment;
import com.example.clone_instagram.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PostMainResponseDto {

    private Long postId;
    private String profileImage;
    private String username;
    private String image;
    private String content;
    private int likePostNum;
    private boolean isLikePost;
    private int commentNum;
    private String latestCmt;
    private String cmtUsername;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostMainResponseDto(Post post, boolean likeCheck, Comment latestCmt){

        this.postId = post.getId();
        this.profileImage = post.getUser().getProfileImage();
        this.username = post.getUser().getUsername();
        this.image = post.getImage();
        this.content = post.getContent();
        this.likePostNum = post.getLikePostList().size();
        this.isLikePost = likeCheck;
        this.commentNum = post.getCommentList().size();

        if (latestCmt == null) {
            this.latestCmt = null;
            this.cmtUsername = null;
        } else {
            this.latestCmt = latestCmt.getComment();
            this.cmtUsername = latestCmt.getUser().getUsername();
        }

        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

    }

}
