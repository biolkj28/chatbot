package com.demo.chatbot.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {
    private Long threadId;
    private String question;
    private String answer;
    private String createdAt;
}
