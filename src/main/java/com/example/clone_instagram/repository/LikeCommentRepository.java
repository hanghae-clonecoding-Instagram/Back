package com.example.clone_instagram.repository;

import com.example.clone_instagram.entity.Comment;
import com.example.clone_instagram.entity.LikeComment;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    LikeComment findByUserAndComment(User user, Comment comment);
    boolean existsByUserAndComment(User user, Comment comment);
}
