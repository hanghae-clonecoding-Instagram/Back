package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.*;
import com.example.clone_instagram.entity.Comment;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.entity.UserRoleEnum;
import com.example.clone_instagram.exception.CustomException;
import com.example.clone_instagram.exception.ErrorCode;
import com.example.clone_instagram.repository.CommentRepository;
import com.example.clone_instagram.repository.LikePostRepository;
import com.example.clone_instagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public PostFeedResponseDto getPosts(User user) {
        PostFeedResponseDto postFeedResponseDto = new PostFeedResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        for(Post post : posts){
            Comment latestCmt = commentRepository.findLatestCmt(post.getId()).orElse(null);
            boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
            postFeedResponseDto.addPostFeed(new PostMainResponseDto(post, likeCheck, latestCmt));
        }
        return postFeedResponseDto;
    }

    @Transactional
    public PostDetailResponseDto createPost(PostRequestDto requestDto, String imgUrl, User user){
        Post post = postRepository.saveAndFlush(new Post(requestDto,user, imgUrl));
        postRepository.save(post);
        return new PostDetailResponseDto(post, false);
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long id, User user){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
        return new PostDetailResponseDto(post, likeCheck);
    }


    @Transactional
    public PostDetailResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        if(postRepository.existsByIdAndUser(id,user)) {
            Post post = postRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new CustomException(ErrorCode.POST_NOT_FOUND)
            );
            post.update(requestDto);
            boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
            return new PostDetailResponseDto(post, likeCheck);
        }else{
            throw  new CustomException(ErrorCode.NOT_ALLOW_UPDATE);
        }

    }
    @Transactional
    public MsgResponseDto deletePost(Long id, User user) {

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.deleteById(id);
            return new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else if (postRepository.existsByIdAndUser(id, user)) {
            postRepository.deleteById(id);
            return new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new CustomException(ErrorCode.NOT_ALLOW_DELETE);
        }
    }
}
