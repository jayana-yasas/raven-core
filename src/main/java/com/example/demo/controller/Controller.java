package com.example.demo.controller;

import com.example.demo.component.OtpComponent;
import com.example.demo.component.SendSMSComponent;
import com.example.demo.dto.request.OtpRequestDto;
import com.example.demo.dto.request.SmsRequestDto;
import com.example.demo.dto.response.OTPResponse;
import com.example.demo.dto.response.SMSReposnse;
import com.example.demo.util.ConsoleOutput;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class Controller {

    private final SendSMSComponent sendSMSComponent;
    private final OtpComponent otpComponent;


    @GetMapping("/sms/message")
    public ResponseEntity<Response> sendMessage(@RequestBody SmsRequestDto smsRequestDto) {
        String traceId = UUID.randomUUID().toString();
        SMSReposnse smsReposnse = sendSMSComponent.sendMessage(smsRequestDto, traceId);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", smsReposnse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sms/otp")
    public ResponseEntity<Response> sendOtp(@RequestBody OtpRequestDto otpRequestDto) {
        String traceId = UUID.randomUUID().toString();
        OTPResponse otpResponse = otpComponent.sendOtp(otpRequestDto, traceId);
        ConsoleOutput.print(otpResponse);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", otpResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sms/otp/verify")
    public ResponseEntity<Response> sendVerify(@RequestBody SmsRequestDto smsRequestDto) {
        String traceId = UUID.randomUUID().toString();
        SMSReposnse smsReposnse = sendSMSComponent.sendOtp(smsRequestDto, traceId);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", smsReposnse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
