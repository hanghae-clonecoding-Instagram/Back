package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.MypageRequestDto;
import com.example.clone_instagram.dto.MypageResponseDto;
import com.example.clone_instagram.dto.PostMypageResponseDto;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.repository.PostRepository;
import com.example.clone_instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public MypageResponseDto getMypage(User user) {
        int postingNum = postRepository.countByUser(user);
        MypageResponseDto mypageResponseDto = new MypageResponseDto(user, postingNum);
        List<Post> posts =  postRepository.findByUser(user);
        for (Post post : posts) {
            mypageResponseDto.addPost(new PostMypageResponseDto(post));
        }
        return mypageResponseDto;
    }

    @Transactional
    public MsgResponseDto updateMypage(MypageRequestDto requestDto, String imgUrl, User user) {
        user.updateMypage(requestDto.getUsername(),imgUrl ,requestDto.getIntroduction());
        userRepository.saveAndFlush(user);
        return new MsgResponseDto("Mypage 수정 성공", HttpStatus.OK.value());
    }
}
