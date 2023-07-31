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
public class SendPhoneOtpComponent {

    private final CheckUserExists checkUserExists;
    private final SaveOtpDetails saveOtpDetails;

    public Boolean send(String traceId, SignUpDto signUpDto) {
        boolean existsByPhoneNumber = checkUserExists.byPhoneNumber(traceId, signUpDto.getPhoneNumber());
        if(existsByPhoneNumber){
            throw new UserException("Phone number is already registered with a user");
        }
        OtpDetails otpDetails = mapTo(signUpDto);

        System.out.println("Sending SMS  sms(222222) to " +signUpDto.getPhoneNumber());
        saveOtpDetails.save(traceId, otpDetails);
        return true;
    }

    private OtpDetails mapTo(SignUpDto signUpDto){
        OtpDetails otpDetails = new OtpDetails();
        otpDetails.setType("PHONE");
        otpDetails.setEmail(signUpDto.getEmail());
        otpDetails.setPhoneNumber(signUpDto.getPhoneNumber());
        otpDetails.setOtp(222222);
        return otpDetails;
    }

}
