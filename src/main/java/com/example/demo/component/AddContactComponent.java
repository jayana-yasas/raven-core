package com.example.demo.component;

import com.example.demo.component.steps.*;
import com.example.demo.dto.request.ContactDto;
import com.example.demo.entity.Contact;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AddContactComponent {

    private final CheckContactExists checkContactExists;
    private final SaveContact saveContact;
    private final DeleteContactTags deleteContactTags;
    private final GetUser getUser;
    private final AddAllContactTags addAllContactTags;


    private Contact mapTo(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setEmail(contactDto.getEmail());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setName(contactDto.getName());
        contact.setUserId(contactDto.getUserId());
        return contact;
    }


    public void saveOrUpdate(String traceId, Long userId, List<ContactDto> contactDtos) {

        contactDtos.forEach(contactDto -> saveOrUpdate(traceId, userId, contactDto));
    }

    public void saveOrUpdate(String traceId, Long userId, ContactDto contactDto) {

        contactDto.setUserId(userId);
        boolean contactExistsForUserByEmail = checkContactExists.checkByEmail(traceId, userId, contactDto.getEmail());
        boolean contactExistsForUserByPhone = checkContactExists.checkByEmail(traceId, userId, contactDto.getPhoneNumber());

        Contact contact;
        if (contactExistsForUserByEmail || contactExistsForUserByPhone) {
            contact = checkContactExists.get(traceId, userId, contactDto.getEmail(), contactDto.getPhoneNumber());
            contact.setName(contactDto.getName());
        } else {
            contact = mapTo(contactDto);
        }

        saveContact.saveOrUpdate(traceId, contact);

        List<String> tags = Arrays.stream(contactDto.getTags()).toList();

        deleteContactTags.byContactId(traceId, contact.getId());

        User user = getUser.byId(traceId, userId);

        addAllContactTags.addAll(traceId, tags, contact ,user);


    }

}
