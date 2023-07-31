package com.example.demo.component.steps;

import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SaveTag {
    private final TagRepository tagRepository;

    public Tag  byTagName(String traceId, String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
         return tagRepository.save(tag);
    }
}
