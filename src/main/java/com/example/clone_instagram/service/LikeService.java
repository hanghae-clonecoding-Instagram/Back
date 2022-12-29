package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.entity.*;
import com.example.clone_instagram.exception.CustomException;
import com.example.clone_instagram.exception.ErrorCode;
import com.example.clone_instagram.repository.CommentRepository;
import com.example.clone_instagram.repository.LikeCommentRepository;
import com.example.clone_instagram.repository.LikePostRepository;
import com.example.clone_instagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public MsgResponseDto likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        LikePost likePost = likePostRepository.findByUserAndPost(user, post);
        if (likePost == null) {
            LikePost likesPost = new LikePost(user, post, true);
            likePostRepository.save(likesPost);
            return new MsgResponseDto("게시글 좋아요", HttpStatus.OK.value());
        } else {
            likePostRepository.deleteById(likePost.getId());
            return new MsgResponseDto("게시글 좋아요 취소", HttpStatus.OK.value());
        }
    }

    @Transactional
    public MsgResponseDto likeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        LikeComment likeComment = likeCommentRepository.findByUserAndComment(user, comment);
        if (likeComment == null) {
            LikeComment likesComment = new LikeComment(user, comment, true);
            likeCommentRepository.save(likesComment);
            return new MsgResponseDto("댓글 좋아요", HttpStatus.OK.value());
        } else {
            likeCommentRepository.deleteById(likeComment.getId());
            return new MsgResponseDto("댓글 좋아요 취소", HttpStatus.OK.value());
        }
    }
}
