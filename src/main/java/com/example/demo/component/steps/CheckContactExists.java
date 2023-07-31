package com.example.demo.component.steps;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CheckContactExists {
    private final ContactRepository contactRepository;

    public Boolean checkByEmail(String traceId, Long userId, String email) {
        return  contactRepository.existsByUserIdAndEmail(userId, email);
    }
    public Boolean checkByPhone(String traceId, Long userId, String phoneNumber) {
        return  contactRepository.existsByUserIdAndPhoneNumber(userId, phoneNumber);
    }
    public Contact get(String traceId, Long userId, String email, String phoneNumber) {
        return  contactRepository.findByUserIdAndEmailOrPhoneNumber(userId, email, phoneNumber)
                .orElseThrow(()-> new RuntimeException("Contact Not Found"));
    }
}
