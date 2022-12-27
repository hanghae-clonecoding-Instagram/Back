package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.CommentListResponseDto;
import com.example.clone_instagram.dto.CommentRequestDto;
import com.example.clone_instagram.dto.CommentResponseDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/{postId}")
    public CommentListResponseDto getComments(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.getComments(postId, userDetails.getUser());
    }

    @PostMapping("/comment/{postId}")
    public CommentListResponseDto createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(postId, requestDto, userDetails.getUser());
    }


    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @Valid  @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    public MsgResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId,  userDetails.getUser());
    }
}
