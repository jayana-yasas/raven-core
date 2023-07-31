package com.example.demo.component;

import com.example.demo.component.steps.CheckOtpExists;
import com.example.demo.dto.request.SignUpDto;
import com.example.demo.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class VerifyEmailOtpComponent {

    private final CheckOtpExists checkOtpExists;

    public boolean verify(String traceId, SignUpDto signUpDto) {
        boolean otpExists = checkOtpExists.byEmail(traceId, signUpDto.getEmail(), signUpDto.getEmailOtp());
        if(!otpExists){
            throw new UserException("Otp is not matched");
        }
        return true;
    }

}
