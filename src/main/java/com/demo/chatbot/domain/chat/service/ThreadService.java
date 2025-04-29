package com.demo.chatbot.domain.chat.service;

import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import com.demo.chatbot.domain.auth.jpa.repo.UserInfoRepository;
import com.demo.chatbot.domain.chat.dto.ChatResponse;
import com.demo.chatbot.domain.chat.dto.ThreadInfoResponse;
import com.demo.chatbot.domain.chat.jpa.entity.ChatInfo;
import com.demo.chatbot.domain.chat.jpa.entity.ThreadInfo;
import com.demo.chatbot.domain.chat.jpa.repo.ChatInfoRepository;
import com.demo.chatbot.domain.chat.jpa.repo.ThreadInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final UserInfoRepository userInfoRepository;
    private final ChatInfoRepository chatInfoRepository;
    private final ThreadInfoRepository threadInfoRepository;

    @Transactional(readOnly = true)
    public Page<ThreadInfoResponse> getThreads(Long userId, Pageable pageable) {
        UserInfo userInfo = getUserInfo(userId);
        Page<ThreadInfo> threads = threadInfoRepository.findAllByUserInfo(userInfo, pageable);

        return threads.map(thread -> {
            List<ChatResponse> chats = chatInfoRepository.findAllByThreadInfo(thread).stream()
                    .map(chat -> ChatResponse.builder()
                            .threadId(thread.getId())
                            .question(chat.getQuestion())
                            .answer(chat.getAnswer())
                            .createdAt(chat.getCreatedAt().toString())
                            .build())
                    .collect(Collectors.toList());

            return ThreadInfoResponse.builder()
                    .threadId(thread.getId())
                    .createdAt(thread.getCreatedAt().toString())
                    .chatCount(chats.size())
                    .chats(chats)
                    .build();
        });
    }

    @Transactional
    public void deleteThread(Long userId, Long threadId) {
        UserInfo userInfo = getUserInfo(userId);
        ThreadInfo thread = threadInfoRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));

        if (!thread.getUserInfo().getId().equals(userInfo.getId())) {
            throw new IllegalArgumentException("You cannot delete this thread");
        }
        chatInfoRepository.deleteAllByThreadInfo(thread);
        threadInfoRepository.delete(thread);
    }

    private UserInfo getUserInfo(Long userId) {
       return userInfoRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found User"));
    }
}
