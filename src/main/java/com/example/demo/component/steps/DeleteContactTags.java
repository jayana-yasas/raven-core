package com.example.demo.component.steps;

import com.example.demo.repository.ContactTagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DeleteContactTags {
    private final ContactTagRepository contactTagRepository;

    public void byContactId(String traceId, Long contactId) {
        contactTagRepository.deleteByContactId(contactId);
    }
}
