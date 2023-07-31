package com.example.demo.component;

import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.ContactTagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DeleteContactComponent {
    private final ContactTagRepository contactTagRepository;
    private final ContactRepository contactRepository;
    @Transactional
    public void deleteContact(String traceId, List<Long> contactIdList) {
        contactTagRepository.deleteByContactIdIn(contactRepository.findAllById(contactIdList));
        contactRepository.deleteByIdIn(contactIdList);
    }

}
