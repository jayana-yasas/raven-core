package com.example.demo.controller;

import com.example.demo.component.CommonComponent;
import com.example.demo.component.OtpComponent;
import com.example.demo.dto.request.SignUpDto;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final CommonComponent component;


    @GetMapping("/signup")
    public ResponseEntity<Response> sendMessage(@RequestBody SignUpDto signUpDto, @RequestAttribute String traceId) {
        component.signUp(traceId, signUpDto);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
