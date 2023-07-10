package com.example.demo.controller;

import com.example.demo.component.CommonComponent;
import com.example.demo.dto.request.ContactDto;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/contact")
public class ContactController {

    private final CommonComponent component;

    @PostMapping()
    public ResponseEntity<Response> contact(@RequestBody ContactDto contactDto, @RequestAttribute String traceId, @RequestAttribute Long userId) {
        component.addContact(traceId, userId, contactDto);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Response> contactP(@RequestBody ContactDto contactDto, @RequestAttribute String traceId, @RequestAttribute Long userId) {
        component.addContact(traceId, userId, contactDto);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Response> contactList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestAttribute String traceId, @RequestAttribute Long userId) {
        System.out.println(userId + " " + traceId);
        Map<String, Object> contactList = component.getLoanByBorrowerId(traceId, page, size);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", contactList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{tagName}")
    public ResponseEntity<Response> contactListWithTag(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3")
    int size, @RequestAttribute String traceId, @RequestAttribute Long userId, @PathVariable String tagName) {
        System.out.println(userId + " " + traceId + " " + tagName);
        Map<String, Object> contactList = component.getContactList(tagName, traceId, page, size);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", contactList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Response> remove(@RequestParam List<Long> contactIds, @RequestAttribute String traceId) {
        component.deleteContact(traceId, contactIds);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
