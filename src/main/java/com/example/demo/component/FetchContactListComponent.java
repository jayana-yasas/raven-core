package com.example.demo.component;

import com.example.demo.component.steps.GetContactListCount;
import com.example.demo.dto.response.ContactResponseDto;
import com.example.demo.dto.response.TagDto;
import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactTag;
import com.example.demo.entity.Tag;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.ContactTagRepository;
import com.example.demo.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class FetchContactListComponent {
    private final ContactRepository contactRepository;
    private final ContactTagRepository contactTagRepository;
    private final TagRepository tagRepository;

    private final GetContactListCount getContactListCount;

    public Map<String, Object> getAllContactList(String traceId, int page, int size, String searchParam, Long userId) {

        int offset = (page * size);

        List<Contact> contactList = contactRepository.fetchPaginatedList(searchParam, size, offset, userId);
        BigInteger count = getContactListCount.bySearchParam(traceId, searchParam, userId);

        List<ContactResponseDto> contactResponseDtoList = contactList.stream().map(c -> {
            List<ContactTag> contactTags = contactTagRepository.findDistinctByContactId_IdAndUser_Id(c.getId(), c.getUserId()) ;
            List<Tag> tags = tagRepository.findByIdIn(contactTags.stream().map(a-> a.getTagId().getId()).toList());

            List<TagDto> tagList = tags.stream().map(a-> {
                TagDto tagDto =new TagDto();
                tagDto.setId(a.getId());
                tagDto.setName(a.getName());
                return tagDto;
            }).toList();

            ContactResponseDto response = ContactResponseDto.builder()
                    .id(c.getId())
                    .contactId(c.getId())
                    .phoneNumber(c.getPhoneNumber())
                    .name(c.getName())
                    .email(c.getEmail())
                    .userId(c.getUserId())
                    .tags(tagList)
                    .build();

            response.setId(c.getId());
            return response;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("contactList", contactResponseDtoList);
        response.put("currentPage", page);
        response.put("totalItems", count);
        response.put("totalPages", (Long.parseLong(count.toString()) / size) + 1);

        return response;
    }

}
