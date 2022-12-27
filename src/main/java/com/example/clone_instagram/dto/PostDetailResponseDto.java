package com.example.clone_instagram.dto;

import com.example.clone_instagram.entity.LikePost;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.repository.LikePostRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponseDto {

    private Long postId;
    private String profileImage;
    private String username;
    private String image;
    private String content;
    private int likePostNum;
    private boolean isLikePost;
    private int commentNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostDetailResponseDto(Post post){


        this.postId = post.getId();
        this.profileImage = post.getUser().getProfileImage();
        this.username = post.getUser().getUsername();
        this.image = post.getImage();
        this.content = post.getContent();
        this.likePostNum = post.getLikePostList().size();
//        this.isLikePost = post.getLikePost().isLikePost();
        this.commentNum = post.getCommentList().size();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();


    }

}
