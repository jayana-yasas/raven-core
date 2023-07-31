package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactResponseDto {

    Long id;
    Long contactId;
    Long userId;
    String email;
    String phoneNumber;
    List<TagDto> tags;
    String name;
}
