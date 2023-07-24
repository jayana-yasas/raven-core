package com.example.demo.component.steps;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class GetUser {
    private final UserRepository userRepository;
    public User  byId(String traceId, Long userId) {
        return userRepository.getById(userId);
    }
}
