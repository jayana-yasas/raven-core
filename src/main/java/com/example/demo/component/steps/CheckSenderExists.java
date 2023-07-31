package com.example.demo.component.steps;

import com.example.demo.entity.Sender;
import com.example.demo.repository.SenderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CheckSenderExists {
    private final SenderRepository senderRepository;

    public Boolean check(String traceId, Long userId, String senderName) {
        return  senderRepository.existsByUserIdAndSenderName(userId, senderName);
    }

    public Boolean checkById(String traceId, Long userId, Long senderId) {
        return  senderRepository.existsByUserIdAndId(userId, senderId);
    }

    public Sender get(String traceId, Long userId, String senderName) {
        return  senderRepository.findByUserIdAndSenderName(userId, senderName)
                .orElseThrow(()-> new RuntimeException("Sender Not Found"));
    }

    public Sender getById(String traceId, Long userId, Long senderId) {
        return  senderRepository.findByUserIdAndId(userId, senderId)
                .orElseThrow(()-> new RuntimeException("Sender Not Found"));
    }
}
