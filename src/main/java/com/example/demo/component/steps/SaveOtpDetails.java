package com.example.demo.component.steps;

import com.example.demo.entity.OtpDetails;
import com.example.demo.repository.OtpDetailRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SaveOtpDetails {
    private final OtpDetailRepository otpDetailRepository;
    public void  save(String traceId, OtpDetails otpDetails) {
        otpDetailRepository.save(otpDetails);
    }
}
