package com.example.clone_instagram.dto;

import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.repository.PostRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MypageResponseDto {

    private String profileImage;
    private String username;
    private int postingNum;
    private String introduction;
    private List<PostMypageResponseDto> postList = new ArrayList<>();

    public MypageResponseDto(User user, int postingNum) {
        this.profileImage = user.getProfileImage();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.postingNum = postingNum;
    }

    public void addPost(PostMypageResponseDto postMypageResponseDto) {postList.add(postMypageResponseDto);}
}
