package com.example.clone_instagram.repository;

import com.example.clone_instagram.entity.Comment;
import com.example.clone_instagram.entity.LikePost;
import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    LikePost findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);
}
