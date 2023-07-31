package com.example.demo.component.steps;

import com.example.demo.repository.SenderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
@AllArgsConstructor
public class GetSenderListCount {
    private final SenderRepository senderRepository;
    public BigInteger  bySearchParam(String traceId, String  senderType, Long userId) {
         return senderRepository.fetchCount(senderType, userId).get(0);

    }
}
