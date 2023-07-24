package com.example.demo.component.steps;

import com.example.demo.dto.request.ContactDto;
import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
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
