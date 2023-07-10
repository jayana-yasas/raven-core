package com.example.demo.controller;

import com.example.demo.component.CommonComponent;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class SegmentController {

    private final CommonComponent component;

    @PostMapping("/segment")
    public ResponseEntity<Response> segment(@RequestBody String name, @RequestAttribute String traceId, @RequestAttribute Long userId) {
        component.addContact(traceId, userId, null);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
