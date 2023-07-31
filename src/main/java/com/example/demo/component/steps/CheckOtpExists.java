package com.example.demo.component.steps;

import com.example.demo.repository.OtpDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CheckOtpExists {
    private final OtpDetailRepository otpDetailRepository;


    public Boolean byEmail(String traceId, String email, Integer otp) {
        return  otpDetailRepository.existsByTypeAndEmailAndOtp("EMAIL", email, otp);
    }

    public Boolean byPhone(String traceId, String phoneNumber, Integer otp) {
        return  otpDetailRepository.existsByTypeAndPhoneNumberAndOtp("PHONE", phoneNumber, otp);
    }
}
