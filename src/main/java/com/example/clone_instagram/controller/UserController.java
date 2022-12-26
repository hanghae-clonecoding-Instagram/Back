package com.example.clone_instagram.controller;

import com.example.clone_instagram.dto.LoginRequestDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.SignupRequestDto;
import com.example.clone_instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public MsgResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }


    @PostMapping("/login")
    public MsgResponseDto login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}
