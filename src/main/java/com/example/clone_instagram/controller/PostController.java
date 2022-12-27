package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.PostDetailResponseDto;
import com.example.clone_instagram.dto.PostFeedResponseDto;
import com.example.clone_instagram.dto.PostRequestDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.PostService;
import com.example.clone_instagram.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.NotFound;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final S3Uploader s3Uploader;
    @GetMapping("/posts")
    public PostFeedResponseDto getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPosts(userDetails.getUser());
    }


//    @PostMapping("/post")
//    public MsgResponseDto createPost(@RequestBody @Valid PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.createPost(requestDto, userDetails.getUser());
//    }

    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostDetailResponseDto createPost(@RequestPart @Valid PostRequestDto requestDto,
                                     @RequestPart (value = "file", required = false) MultipartFile multipartFile,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String imgUrl= s3Uploader.upload(multipartFile, "static");
        return postService.createPost(requestDto, imgUrl, userDetails.getUser());
    }

    @GetMapping("/post/{id}")
    public PostDetailResponseDto getPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPost(id, userDetails.getUser());
    }

    @PutMapping(value = "/post/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostDetailResponseDto  updatePost(@PathVariable Long id,
                                             @RequestPart @Valid PostRequestDto requestDto,
                                             @RequestPart (value = "file", required = false) MultipartFile multipartFile,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        String imgUrl= s3Uploader.upload(multipartFile, "static");
        return postService.updatePost(id,requestDto, imgUrl, userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    public MsgResponseDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getUser());
    }
}
