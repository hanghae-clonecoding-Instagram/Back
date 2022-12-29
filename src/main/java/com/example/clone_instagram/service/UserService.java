package com.example.clone_instagram.service;

import com.example.clone_instagram.dto.LoginRequestDto;
import com.example.clone_instagram.dto.MsgResponseDto;
import com.example.clone_instagram.dto.SignupRequestDto;
import com.example.clone_instagram.entity.User;
import com.example.clone_instagram.entity.UserRoleEnum;
import com.example.clone_instagram.exception.CustomException;
import com.example.clone_instagram.exception.ErrorCode;
import com.example.clone_instagram.jwt.JwtUtil;
import com.example.clone_instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${admin.secret.token}")
    private String adminToken;

    @Transactional
    public MsgResponseDto signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        boolean isExistEmail = userRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new CustomException(ErrorCode.OVERLAP_EMAIL);
        }

        boolean isExistUsername = userRepository.existsByUsername(username);
        if (isExistUsername) {
            throw new CustomException(ErrorCode.OVERLAP_USERNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()){
            if (!requestDto.getAdminToken().equals(adminToken)) {
                throw new CustomException(ErrorCode.MISMATCH_ADMIN_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }


        User user = new User(email, username, password, role);
        userRepository.save(user);

        return new MsgResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public MsgResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        //사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        //비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));

//        //1. Login ID/PW 를 기반으로 AuthenticationToken 생성
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
//
//        //2. 실제로 검증(사용자 비밀번호 체크)이 이루어지는 부분
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        //3. 토큰을 생성하여 Header 에 저장
//        response.addHeader(
//                JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(authentication));

        return new MsgResponseDto("로그인 성공", HttpStatus.OK.value());
    }

}
