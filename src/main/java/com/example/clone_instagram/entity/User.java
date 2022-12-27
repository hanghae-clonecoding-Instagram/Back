package com.example.clone_instagram.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduction="";

    @Column(nullable = false)
    private String profileImage="https://wooo96bucket.s3.ap-northeast-2.amazonaws.com/static/Default_pfp.jpg";

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String email, String username, String password, UserRoleEnum role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void updateMypage(String username, String profileImage, String introduction) {
        this.username = username;
        this.profileImage = profileImage;
        this.introduction = introduction;
    }

}
