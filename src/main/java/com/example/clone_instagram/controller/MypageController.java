package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.MypageRequestDto;
import com.example.clone_instagram.dto.MypageResponseDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.MypageService;
import com.example.clone_instagram.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;
    private final S3Uploader s3Uploader;

    @GetMapping("/mypage")
    public MypageResponseDto getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getMypage(userDetails.getUser());
    }

    @PutMapping(value = "/mypage", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public MsgResponseDto updateMypage(@Valid @RequestPart MypageRequestDto requestDto,
                                       @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String imgUrl=s3Uploader.upload(multipartFile, "static");
        return mypageService.updateMypage(requestDto, imgUrl, userDetails.getUser());
    }
}


