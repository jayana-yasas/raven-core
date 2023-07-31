package com.example.demo.component.steps;

import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CheckUserExists {
    private final UserRepository userRepository;


    public Boolean byEmail(String traceId, String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean byPhoneNumber(String traceId, String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
