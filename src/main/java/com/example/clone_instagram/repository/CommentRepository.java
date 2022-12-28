package com.example.clone_instagram.repository;

import com.example.clone_instagram.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByModifiedAtDesc(Long postId);

    @Query(nativeQuery = true, value="select * from comment where post_id = :post_id order by modified_at desc limit 1")
    Optional<Comment> findLatestCmt(@Param("post_id")Long postId);
}
