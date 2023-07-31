package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SenderResponseDto {

    Long id;
    String senderName;
    String status;
}
