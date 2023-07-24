package com.example.demo.component.steps;

import com.example.demo.dto.request.ContactDto;
import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SaveContact {
    private final ContactRepository contactRepository;
    private final CheckContactExists checkContactExists;

    public Contact saveOrUpdate(String traceId, Contact contact) {
        return  contactRepository.saveAndFlush(contact);
    }

}
