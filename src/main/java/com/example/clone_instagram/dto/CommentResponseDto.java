package com.example.clone_instagram.dto;

import com.example.clone_instagram.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String profileImage;
    private String username;
    private String comment;
    private int likeCmtNum;
    private boolean isLikeCmt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {

        this.commentId = comment.getId();
        this.profileImage = comment.getUser().getProfileImage();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.likeCmtNum = comment.getLikeCommentList().size();
//        this.isLikeCmt = comment.getLikeComment().isLikeCmt();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();


    }
}
