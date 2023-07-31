package com.example.demo.component.steps;

import com.example.demo.entity.Sender;
import com.example.demo.repository.SenderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SaveSender {
    private final SenderRepository senderRepository;

    public Sender saveOrUpdate(String traceId, Sender sender) {
        return  senderRepository.saveAndFlush(sender);
    }

}
