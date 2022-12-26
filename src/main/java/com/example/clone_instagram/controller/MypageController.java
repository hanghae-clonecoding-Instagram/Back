package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.MypageRequestDto;
import com.example.clone_instagram.dto.MypageResponseDto;
import com.example.clone_instagram.security.UserDetailsImpl;
import com.example.clone_instagram.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/")
    public MypageResponseDto getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getMypage(userDetails.getUser());
    }

    @PutMapping("/")
    public MsgResponseDto updateMypage(@Valid @RequestBody MypageRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.updateMypage(requestDto, userDetails.getUser());
    }
}


