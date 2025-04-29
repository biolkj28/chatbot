package com.demo.chatbot.domain.auth.controller;

import com.demo.chatbot.domain.auth.dto.LoginRequest;
import com.demo.chatbot.domain.auth.dto.SignUpRequest;
import com.demo.chatbot.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력해서 회원가입을 합니다.")
    @PostMapping("/signup")
    public void signup(@Validated @RequestBody SignUpRequest request) {
        authService.signup(request);
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호, 로그인 합니다.")
    @PostMapping("/login")
    public String login(@Validated @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
