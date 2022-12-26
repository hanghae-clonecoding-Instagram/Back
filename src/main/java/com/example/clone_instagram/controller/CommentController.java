package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.CommentListResponseDto;
import com.example.clone_instagram.dto.CommentRequestDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/{postId}")
    public CommentListResponseDto createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(postId, requestDto, userDetails.getUser());
    }
    //댓글 수정
    @PutMapping("/{commentId}")
    public CommentListResponseDto updateComment(@PathVariable Long id, @Valid  @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }
    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public MsgResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id,  userDetails.getUser());
    }
}
