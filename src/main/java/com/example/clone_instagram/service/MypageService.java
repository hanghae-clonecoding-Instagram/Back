package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.MypageRequestDto;
import com.example.clone_instagram.dto.MypageResponseDto;
import com.example.clone_instagram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MypageService {

    @Transactional
    public MypageResponseDto getMypage(User user) {
       return new MypageResponseDto(user);
    }

    public MsgResponseDto updateMypage(MypageRequestDto requestDto, User user) {
        user.updateMypage(requestDto.getUsername(), requestDto.getProfileImage(), requestDto.getIntroduction());
        return new MsgResponseDto("Mypage 수정 성공", HttpStatus.OK.value());
    }
}
