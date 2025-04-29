package com.demo.chatbot.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @Email(message = "Required Email")
    private String email;

    @NotBlank(message = "Required Password")
    private String password;

    @NotBlank(message = "Required Name")
    private String name;
}
