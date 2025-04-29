package com.demo.chatbot.domain.auth.jpa.repo;

import com.demo.chatbot.domain.auth.jpa.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
}
