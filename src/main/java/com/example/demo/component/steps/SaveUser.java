package com.example.demo.component.steps;

import com.example.demo.dto.request.SignUpDto;
import com.example.demo.entity.OtpDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.OtpDetailRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SaveUser {
    private final UserRepository userRepository;
    public void  save(String traceId, User user) {
        userRepository.save(user);
    }
}
