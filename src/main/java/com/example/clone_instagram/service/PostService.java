package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.PostDetailResponseDto;
import com.example.clone_instagram.dto.PostFeedResponseDto;
import com.example.clone_instagram.dto.PostRequestDto;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.entity.UserRoleEnum;
import com.example.clone_instagram.repository.LikePostRepository;
import com.example.clone_instagram.repository.PostRepository;
import com.example.clone_instagram.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;

    @Transactional(readOnly = true)
    public PostFeedResponseDto getPosts(User user) {
        PostFeedResponseDto postFeedResponseDto = new PostFeedResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        for(Post post : posts){
            boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
            postFeedResponseDto.addPostFeed(new PostDetailResponseDto(post, likeCheck));
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
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
        return new PostDetailResponseDto(post, likeCheck);
    }


    @Transactional
    public PostDetailResponseDto updatePost(Long id, PostRequestDto requestDto, String imgUrl, User user) {
        if(postRepository.existsByIdAndUser(id,user)) {
            Post post = postRepository.findByIdAndUser(id, user).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다.")
            );
            post.update(requestDto, imgUrl);
            boolean likeCheck = likePostRepository.existsByUserAndPost(user, post);
            return new PostDetailResponseDto(post, likeCheck);
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
