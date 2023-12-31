package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpDto {
    String email;
    Integer emailOtp;
    String password;
    String name;
    String phoneNumber;
    Integer phoneNumberOtp;
//    String nic;
}
