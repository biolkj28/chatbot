package com.demo.chatbot.domain.auth.controller;

import com.demo.chatbot.domain.auth.dto.LoginRequest;
import com.demo.chatbot.domain.auth.dto.SignUpRequest;
import com.demo.chatbot.domain.auth.service.AuthService;
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

    @PostMapping("/signup")
    public void signup(@Validated @RequestBody SignUpRequest request) {
        authService.signup(request);
    }

    @PostMapping("/login")
    public String login(@Validated @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
