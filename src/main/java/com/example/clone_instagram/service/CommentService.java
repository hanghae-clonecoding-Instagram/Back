package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.CommentListResponseDto;
import com.example.clone_instagram.dto.CommentRequestDto;
import com.example.clone_instagram.dto.CommentResponseDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.entity.Comment;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.entity.UserRoleEnum;
import com.example.clone_instagram.exception.CustomException;
import com.example.clone_instagram.exception.ErrorCode;
import com.example.clone_instagram.repository.CommentRepository;
import com.example.clone_instagram.repository.LikeCommentRepository;
import com.example.clone_instagram.repository.PostRepository;
import com.example.clone_instagram.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeCommentRepository likeCommentRepository;


    @Transactional
    public CommentListResponseDto getComments(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
        List<Comment> comments = commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId);
        for(Comment comment2 : comments) {
            boolean likeCheck = likeCommentRepository.existsByUserAndComment(user, comment2);
            commentListResponseDto.addCommentList(new CommentResponseDto(comment2, likeCheck));
        }

        return commentListResponseDto;

    }

    @Transactional
    public CommentListResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comment = new Comment(requestDto, post, user);
        commentRepository.save(comment);

        CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
        List<Comment> comments = commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId);
        for(Comment comment2 : comments) {
            boolean likeCheck = likeCommentRepository.existsByUserAndComment(user, comment2);
            commentListResponseDto.addCommentList(new CommentResponseDto(comment2, likeCheck));
        }

        return commentListResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        String commentWriter = comment.getUser().getUsername();

        if (commentWriter.equals(user.getUsername())) {
            comment.update(requestDto);
            boolean likeCheck = likeCommentRepository.existsByUserAndComment(user, comment);
            return new CommentResponseDto(comment,likeCheck);
        } else {
            throw new CustomException(ErrorCode.NOT_ALLOW_UPDATE);
        }
    }

    @Transactional
    public MsgResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        String commentWriter = comment.getUser().getUsername();

        if (commentWriter.equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            commentRepository.deleteById(id);
            return new MsgResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new CustomException(ErrorCode.NOT_ALLOW_DELETE);
        }
    }

}