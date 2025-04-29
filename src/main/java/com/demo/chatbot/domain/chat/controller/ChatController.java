package com.demo.chatbot.domain.chat.controller;

import com.demo.chatbot.domain.auth.dto.CustomUserDetails;
import com.demo.chatbot.domain.chat.dto.ChatRequest;
import com.demo.chatbot.domain.chat.dto.ChatResponse;
import com.demo.chatbot.domain.chat.dto.ThreadInfoResponse;
import com.demo.chatbot.domain.chat.service.ChatService;
import com.demo.chatbot.domain.chat.service.ThreadService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ThreadService threadService;

    @Operation(summary = "채팅 생성", description = "채팅 생성 API")
    @PostMapping
    public ChatResponse createChat(@RequestBody @Valid ChatRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return chatService.createChatInfo(userDetails.getUserId(), request);
    }

    @Operation(summary = "쓰레드 조회", description = "쓰레드 조회 API")
    @GetMapping
    public Page<ThreadInfoResponse> getChats(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam(defaultValue = "desc") String sort,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        Sort.Direction sortDirection;
        if (sort != null && !sort.isEmpty() && !sort.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, sortDirection);
        return threadService.getThreads(userDetails.getUserId(), pageable);
    }

    @Operation(summary = "쓰레드 삭제", description = "쓰레드 삭제/ 하위 채팅 제거 API")
    @DeleteMapping("/{threadId}")
    public void deleteThread(@PathVariable Long threadId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        threadService.deleteThread(userDetails.getUserId(), threadId);
    }
}
