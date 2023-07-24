package com.example.demo.component.steps;

import com.example.demo.component.ContactTagComponent;
import com.example.demo.dto.request.ContactDto;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.ContactTagRepository;
import com.example.demo.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AddAllContactTags {
    private final TagRepository tagRepository;
    private final ContactTagComponent contactTagComponent;
    private final SaveTag saveTag;

    public void addAll(String traceId, List<String> tags, Contact contact, User user) {
        for (String tagName : tags) {
            List<Tag> tagtList = tagRepository.findAllByName(tagName);
            if (!tagtList.isEmpty()) {
                contactTagComponent.create(traceId, contact, tagtList.get(0), user);
            } else {
                Tag tag = saveTag.byTagName(traceId, tagName);
                contactTagComponent.create(traceId, contact, tag, user);
            }
        }
    }
}
