package com.example.demo.controller;

import com.example.demo.component.SendSMSComponent;
import com.example.demo.dto.request.SmsRequestDto;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/send-sms")
public class SendSMSController {
    private static final String RESPONSE_SUCCESS = "Successful Execution";
    private final SendSMSComponent sendSMSComponent;

    @PostMapping()
    public ResponseEntity<Response> addSender(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestBody SmsRequestDto smsRequestDto) {

        sendSMSComponent.sendMessage(smsRequestDto, traceId);

        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
