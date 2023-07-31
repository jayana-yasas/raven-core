package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignDto {

    Long campaignId;
    String type;
    String channel;
    String name;
    String sender;
    Long[] toTags;
    String[] toNumbers;
    Boolean isScheduled;
    String scheduledAt;//yyy-mm-dd hh:mm:ss
    Content content;

    @Data
    @Builder
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content{
        String template;
        Integer pageCount;
        Integer characterCount;
        Boolean hasUnicode;
        List<UrlsForShort> urlsForShort;
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UrlsForShort{
        String key;
        String originalURL;
    }
}

