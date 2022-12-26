package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    //좋아요 게시글
    @PostMapping("/post/{postId}")
    public MsgResponseDto likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postId, userDetails.getUser());
    }

    //좋아요 코멘트
    @PostMapping ("/comment/{commentId}")
    public MsgResponseDto likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeComment(commentId, userDetails.getUser());
    }
}
