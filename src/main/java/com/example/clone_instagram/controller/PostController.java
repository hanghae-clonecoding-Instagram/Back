package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.PostDetailResponseDto;
import com.example.clone_instagram.dto.PostFeedResponseDto;
import com.example.clone_instagram.dto.PostRequestDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public PostFeedResponseDto getPosts(){
        return postService.getPosts();
    }

    @PostMapping("/post")
    public PostDetailResponseDto createPost(@RequestBody @Valid PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(requestDto, userDetails.getUser());
    }

    @GetMapping("/post/{id}")
    public PostDetailResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/post/{id}")
    public PostDetailResponseDto  updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(id,requestDto,userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    public MsgResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getUser());
    }
}
