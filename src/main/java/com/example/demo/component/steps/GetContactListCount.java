package com.example.demo.component.steps;

import com.example.demo.entity.User;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class GetContactListCount {
    private final ContactRepository contactRepository;
    public BigInteger  bySearchParam(String traceId, String  searchParam) {
         return contactRepository.fetchCount(searchParam).get(0);

    }
}
