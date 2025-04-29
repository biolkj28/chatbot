package com.demo.chatbot.domain.chat.controller;

import com.demo.chatbot.domain.auth.dto.CustomUserDetails;
import com.demo.chatbot.domain.chat.dto.ChatRequest;
import com.demo.chatbot.domain.chat.dto.ChatResponse;
import com.demo.chatbot.domain.chat.dto.ThreadInfoResponse;
import com.demo.chatbot.domain.chat.service.ChatService;
import com.demo.chatbot.domain.chat.service.ThreadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ThreadService threadService;

    @PostMapping
    public ChatResponse createChat(@RequestBody @Valid ChatRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return chatService.createChatInfo(userDetails.getUserId(), request);
    }

    @GetMapping
    public Page<ThreadInfoResponse> getChats(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam(defaultValue = "desc") String sort,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        Sort.Direction sortDirection;
        if (sort != null && !sort.isEmpty() && !sort.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }else{
            sortDirection = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size,sortDirection);
        return threadService.getThreads(userDetails.getUserId(), pageable);
    }

    @DeleteMapping("/{threadId}")
    public void deleteThread(@PathVariable Long threadId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        threadService.deleteThread(userDetails.getUserId(), threadId);
    }
}
