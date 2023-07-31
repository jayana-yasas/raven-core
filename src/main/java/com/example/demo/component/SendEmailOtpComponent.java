package com.example.demo.component;

import com.example.demo.component.steps.CheckUserExists;
import com.example.demo.component.steps.SaveOtpDetails;
import com.example.demo.dto.request.SignUpDto;
import com.example.demo.entity.OtpDetails;
import com.example.demo.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SendEmailOtpComponent {

    private final CheckUserExists checkUserExists;
    private final SaveOtpDetails saveOtpDetails;

    public Boolean send(String traceId, SignUpDto signUpDto) {
        boolean existsByEmail = checkUserExists.byEmail(traceId, signUpDto.getEmail());
        if(existsByEmail){
            throw new UserException("Email is already registered with a user");
        }
        OtpDetails otpDetails = mapTo(signUpDto);

        System.out.println("Sending email otp(11111) to " +signUpDto.getEmail());
        saveOtpDetails.save(traceId, otpDetails);
        return true;
    }

    private OtpDetails mapTo(SignUpDto signUpDto){
        OtpDetails otpDetails = new OtpDetails();
        otpDetails.setType("EMAIL");
        otpDetails.setEmail(signUpDto.getEmail());
        otpDetails.setPhoneNumber(null);
        otpDetails.setOtp(11111);
        return otpDetails;
    }

}
