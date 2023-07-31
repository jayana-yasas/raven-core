package com.example.demo.controller;

import com.example.demo.component.*;
import com.example.demo.config.jwt.JwtTokenUtil;
import com.example.demo.dto.jwt.JwtRequest;
import com.example.demo.dto.jwt.JwtResponse;
import com.example.demo.dto.request.SignUpDto;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final CommonComponent component;
    private final SendEmailOtpComponent sendEmailOtpComponent;
    private final VerifyEmailOtpComponent verifyEmailOtpComponent;
    private final VerifyPhoneOtpComponent verifyPhoneOtpComponent;
    private final SendPhoneOtpComponent sendPhoneOtpComponent;
    private final SignUpUserComponent signUpUserComponent;


    @PostMapping("/user/auth/send-email-otp") //send email otp
    public ResponseEntity<Response> sendEmailOtp(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        sendEmailOtpComponent.send(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("OTP Email is Sent", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/auth/verify-email")
    public ResponseEntity<Response> verifyEmail(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        Boolean isVerified = verifyEmailOtpComponent.verify(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("Email Verification is Successful", isVerified);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/auth/send-phone-otp") //send phone otp
    public ResponseEntity<Response> sendPhoneOtp(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        sendPhoneOtpComponent.send(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("OTP SMS is Sent", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/auth/verify-phone")
    public ResponseEntity<Response> verifyPhone(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        Boolean isVerified = verifyPhoneOtpComponent.verify(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("Phone Verification is Successful", isVerified);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/user/auth/sign-up")
    public ResponseEntity<Response> signup(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        signUpUserComponent.signUp(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("User Registration is Successful", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<Response> login(@RequestBody JwtRequest authenticationRequest) {

        if (component.is(authenticationRequest.getUsername())) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(
                    String.valueOf(component.getotpUser(authenticationRequest.getUsername()).getId()));
            final String token = jwtTokenUtil.generateToken(userDetails);
            Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", new JwtResponse(token));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = ResponseBuilder.composeFailureResponse("Failed Authenticate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/oauth2/authorization/google")
    public Principal user(Principal principal) {
        return principal;
    }

}
