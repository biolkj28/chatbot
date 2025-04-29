package com.demo.chatbot.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ThreadInfoResponse {
    private Long threadId;
    private String createdAt;
    private int chatCount;
    private List<ChatResponse> chats;
}
