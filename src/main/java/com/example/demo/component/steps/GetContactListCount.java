package com.example.demo.component.steps;

import com.example.demo.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
@AllArgsConstructor
public class GetContactListCount {
    private final ContactRepository contactRepository;
    public BigInteger  bySearchParam(String traceId, String  searchParam, Long userId) {
         return contactRepository.fetchCount(searchParam, userId).get(0);

    }
}
