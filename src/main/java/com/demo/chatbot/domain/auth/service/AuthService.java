package com.demo.chatbot.domain.auth.service;

import com.demo.chatbot.domain.auth.dto.LoginRequest;
import com.demo.chatbot.domain.auth.dto.SignUpRequest;
import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import com.demo.chatbot.domain.auth.jpa.repo.UserInfoRepository;
import com.demo.chatbot.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(SignUpRequest request) {
        UserInfo user = UserInfo.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role("ROLE_MEMBER") // 기본 사용자 권한
                .build();
        userInfoRepository.save(user);
    }

    public String login(LoginRequest request) {
        UserInfo user = userInfoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtProvider.generateToken(user.getEmail(), user.getId(), List.of(user.getRole()));
    }
}
