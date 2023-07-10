package com.example.demo.controller;

import com.example.demo.component.CommonComponent;
import com.example.demo.config.jwt.JwtTokenUtil;
import com.example.demo.dto.jwt.JwtRequest;
import com.example.demo.dto.jwt.JwtResponse;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private UserDetailsService userDetailsService1;
    @Autowired
    private CommonComponent commonComponent;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<Response> createAccessToken(@RequestBody JwtRequest authenticationRequest) {

        if (commonComponent.is(authenticationRequest.getUsername())) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(
                    String.valueOf(commonComponent.getotpUser(authenticationRequest.getUsername()).getId()));
            final String token = jwtTokenUtil.generateToken(userDetails);
            Response response = ResponseBuilder.composeSuccessResponse("Successful Authenticate", new JwtResponse(token));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = ResponseBuilder.composeFailureResponse("Failed Authenticate", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

}
