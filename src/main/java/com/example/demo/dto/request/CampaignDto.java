package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignDto {

    String type;
    String name;
    String sender;
    String[] toTags;
    Boolean isScheduled;
    String scheduledAt;//yyy-mm-dd hh:mm:ss
    String template;

}

