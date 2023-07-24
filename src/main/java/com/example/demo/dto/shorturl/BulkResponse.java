package com.example.demo.dto.shorturl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkResponse {
    private LocalDateTime createdAt;
    private Long DomainId;
    private Boolean archived;
    private Long OwnerId;
    private LocalDateTime updatedAt;
    private String originalURL;
    private String[] tags;
    private Boolean cloaking;
    private String path;
    private String idString;
    private String shortURL;
    private String secureShortURL;
    private Boolean duplicate;
    private Boolean success;

}
