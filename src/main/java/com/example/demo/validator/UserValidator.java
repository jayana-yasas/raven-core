package com.example.demo.validator;

import com.example.demo.dto.request.SignUpDto;
import com.example.demo.entity.OtpUser;
import com.example.demo.exception.UserException;
import com.example.demo.repository.OtpUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class UserValidator {
    private final OtpUserRepository otpUserRepository;

    public void validatePhone(String traceId, String phoneNumber) {
        if (phoneNumber.startsWith("94") && phoneNumber.length() == 11) {

        } else {
            throw new UserException("Phone number is not valid");
        }
    }

    public void validateEmail(String traceId, SignUpDto signUpDto) {
        if (signUpDto.getEmail().isEmpty()) {
            throw new UserException("Email is not valid");
        }
    }

    public void validatePassword(String traceId, SignUpDto signUpDto) {
        if (signUpDto.getPassword().isEmpty()) {
            throw new UserException("Email is not valid");
        }
    }

    public void checkAlreadyExists(String traceId, SignUpDto signUpDto) {

        List<OtpUser> otpUserList = otpUserRepository.findAllByEmail(signUpDto.getEmail());

        if (!otpUserList.isEmpty()) {
            throw new UserException("Email already registered");
        }
    }
}
