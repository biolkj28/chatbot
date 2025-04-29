package com.demo.chatbot.domain.chat.jpa.entity;

import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ThreadInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}

