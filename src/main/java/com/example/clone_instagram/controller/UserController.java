package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.LoginRequestDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.SignupRequestDto;
import com.example.clone_instagram.jwt.JwtUtil;
import com.example.clone_instagram.service.KakaoService;
import com.example.clone_instagram.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public MsgResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }


    @PostMapping("/login")
    public MsgResponseDto login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
    //카카오로그인
    @GetMapping("/kakao/callback")
    public MsgResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return new MsgResponseDto("회원가입 성공", HttpStatus.OK.value());
    }
}
