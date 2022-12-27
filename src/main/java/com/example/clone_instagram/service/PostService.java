package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.PostDetailResponseDto;
import com.example.clone_instagram.dto.PostFeedResponseDto;
import com.example.clone_instagram.dto.PostRequestDto;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.entity.UserRoleEnum;
import com.example.clone_instagram.repository.PostRepository;
import com.example.clone_instagram.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostFeedResponseDto getPosts() {
        PostFeedResponseDto postFeedResponseDto = new PostFeedResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        for(Post post : posts){
            postFeedResponseDto.addPostFeed(new PostDetailResponseDto(post));
        }
        return postFeedResponseDto;
    }

    @Transactional
    public PostDetailResponseDto createPost(PostRequestDto requestDto, User user){
        Post post = postRepository.saveAndFlush(new Post(requestDto,user));
        postRepository.save(post);
        return new PostDetailResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return new PostDetailResponseDto(post);
    }


    @Transactional
    public PostDetailResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        if(postRepository.existsByIdAndUser(id,user)) {
            Post post = postRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다.")
            );
            post.update(requestDto);
            return new PostDetailResponseDto(post);
        }else{
            throw  new IllegalArgumentException("해당 게시글이 없습니다.");
        }

    }
    @Transactional
    public MsgResponseDto deletePost(Long id, User user) {
        if(postRepository.existsById(id)){
            Post post;
            if(user.getRole().equals(UserRoleEnum.ADMIN)) {

                post = postRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다.")
                );

            }else{
                post = postRepository.findByIdAndUser(id, user).orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다.")
                );
            }
            postRepository.delete(post);
            return  new MsgResponseDto("게시글 삭제 성공",HttpStatus.OK.value());
        }else{
            throw  new IllegalArgumentException("해당 게시글이 없습니다.");
        }

    }

}
