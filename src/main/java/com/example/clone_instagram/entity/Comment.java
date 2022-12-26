package com.example.clone_instagram.entity;

import javax.persistence.*;

import com.example.clone_instagram.dto.CommentRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likeComment_id", nullable = false)
    private LikeComment likeComment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<LikeComment> likeCommentList = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.comment = requestDto.getComment();
        this.post = post;
        this.user = user;

    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}

