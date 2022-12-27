package com.example.clone_instagram.repository;

import com.example.clone_instagram.entity.Post;
import com.example.clone_instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByModifiedAtDesc();

    boolean existsByIdAndUser(Long id, User user);

    Optional<Post> findByIdAndUser(Long id, User user);

    List<Post> findByUser(User user);
    int countByUser(User user);
}
