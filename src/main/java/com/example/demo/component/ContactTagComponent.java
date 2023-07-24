package com.example.demo.component;

import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactTag;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.ContactTagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ContactTagComponent {

    private final ContactTagRepository contactTagRepository;

    public ContactTag create(String traceId, Contact contact, Tag tag, User user) {
        List<ContactTag> contactTagList = contactTagRepository.findByContactId_IdAndTagId_IdAndUser_Id(contact.getId(), tag.getId(), user.getId());

        if (contactTagList == null || contactTagList.isEmpty()){
            ContactTag contactTag = new ContactTag();
            contactTag.setContactId(contact);
            contactTag.setTagId(tag);
            contactTag.setUser(user);
            return contactTagRepository.saveAndFlush(contactTag);
        } else {
            return contactTagList.stream().findFirst().orElseThrow(() -> new RuntimeException("Contact Tag Not Found"));
        }

    }



}
