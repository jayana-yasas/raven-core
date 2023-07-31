package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SenderDto {

    Long senderId;
    String senderType;
    Long userId;
    String sendEmail;
    String senderName;
    String senderPhone;
    Boolean iddEnabled;
    String purpose;
    SendingAs sendingAs;

    @Data
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SendingAs{
        String sendingAsType;
        Business business;
        Individual individual;
    }

    @Data
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Business{
        String businessLegalName;
        String businessType;
        String authorizedPersonName;
        String authorizedPersonDesignation;
        String legalDocumentType;
        String legalDocumentId;
    }

    @Data
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Individual {
        String fullName;
        String legalDocumentType;
        String legalDocumentId;
    }
}
