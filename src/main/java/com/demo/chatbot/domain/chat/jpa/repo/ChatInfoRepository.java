package com.demo.chatbot.domain.chat.jpa.repo;

import com.demo.chatbot.domain.chat.jpa.entity.ChatInfo;
import com.demo.chatbot.domain.chat.jpa.entity.ThreadInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatInfoRepository extends CrudRepository<ChatInfo, Long> {

    List<ChatInfo>findAllByThreadInfo(ThreadInfo threadInfo);
    Optional<ChatInfo> findTopByThreadInfoOrderByCreatedAtDesc(ThreadInfo threadInfo);
    void deleteAllByThreadInfo(ThreadInfo threadInfo);
}
