package com.demo.chatbot.domain.chat.jpa.repo;

import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import com.demo.chatbot.domain.chat.jpa.entity.ThreadInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThreadInfoRepository extends JpaRepository<ThreadInfo, Long> {
    Optional<ThreadInfo> findTopByUserInfoOrderByCreatedAtDesc(UserInfo userInfo);

    Page<ThreadInfo> findAllByUserInfo(UserInfo userInfo, Pageable pageable);
}
