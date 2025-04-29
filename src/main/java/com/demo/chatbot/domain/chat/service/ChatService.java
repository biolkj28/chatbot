package com.demo.chatbot.domain.chat.service;

import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import com.demo.chatbot.domain.auth.jpa.repo.UserInfoRepository;
import com.demo.chatbot.domain.chat.dto.ChatRequest;
import com.demo.chatbot.domain.chat.dto.ChatResponse;
import com.demo.chatbot.domain.chat.jpa.entity.ChatInfo;
import com.demo.chatbot.domain.chat.jpa.entity.ThreadInfo;
import com.demo.chatbot.domain.chat.jpa.repo.ChatInfoRepository;
import com.demo.chatbot.domain.chat.jpa.repo.ThreadInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserInfoRepository userInfoRepository;
    private final ChatInfoRepository chatInfoRepository;
    private final ThreadInfoRepository threadInfoRepository;

    @Transactional
    public ChatResponse createChatInfo(Long userId, ChatRequest request) {
        UserInfo userInfo = getUserInfo(userId);
        ThreadInfo latestThread = threadInfoRepository.findTopByUserInfoOrderByCreatedAtDesc(userInfo).orElse(null);

        if (isNeedNewThread(latestThread)) {
            latestThread = new ThreadInfo(userInfo);
            threadInfoRepository.save(latestThread);
        }

        // 2. 답변 생성 (모델을 파라미터로 받아서 해당 모델 API 호출 후 답변)
        String answer = "This is an AI-generated answer.";

        // 3. 대화 저장
        ChatInfo chat = ChatInfo.builder()
                .threadInfo(latestThread)
                .question(request.getQuestion())
                .answer(answer)
                .build();
        chatInfoRepository.save(chat);

        return ChatResponse.builder()
                .threadId(Objects.requireNonNull(latestThread).getId())
                .question(chat.getQuestion())
                .answer(chat.getAnswer())
                .createdAt(chat.getCreatedAt().toString())
                .build();
    }

    @Transactional
    public boolean isNeedNewThread(ThreadInfo latestThread) {
        boolean needNewThread = false;
        if (latestThread == null) {
            needNewThread = true;
        } else {
            ChatInfo lastChat = chatInfoRepository.findTopByThreadInfoOrderByCreatedAtDesc(latestThread).orElse(null);

            if (lastChat == null) {
                needNewThread = true;
            } else {
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(lastChat.getCreatedAt(), now);
                if (duration.toMinutes() > 30) {
                    needNewThread = true;
                }
            }
        }
        return needNewThread;
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        return userInfoRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}