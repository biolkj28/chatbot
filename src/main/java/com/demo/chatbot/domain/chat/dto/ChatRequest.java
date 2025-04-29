package com.demo.chatbot.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChatRequest {

    @NotBlank(message = "Question must not be blank")
    private String question;

    private boolean isStreaming;

    @NotBlank(message = "Model must not be blank")
    private String model;
}
