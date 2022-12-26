package com.example.clone_instagram.entity;

import com.example.clone_instagram.dto.PostRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likePost_id", nullable = false)
    private LikePost likePost;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<LikePost> likePostList = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user) {
        this.image = requestDto.getImage();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.image = requestDto.getImage();
        this.content = requestDto.getContent();
    }

}
