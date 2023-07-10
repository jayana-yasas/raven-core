package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSReposnse {
    String logId;
    Double cost;
    Double numberOfUnits;
    Double numberOfPhoneNumbers;
    String referenceId;
    String traceId;
}
