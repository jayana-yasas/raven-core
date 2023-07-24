package com.example.demo.controller;

import com.example.demo.component.AddContactComponent;
import com.example.demo.component.CommonComponent;
import com.example.demo.component.FetchContactListComponent;
import com.example.demo.dto.request.ContactDto;
import com.example.demo.dto.request.ContactTagsDto;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/contact")
public class ContactController {
    private static final String RESPONSE_SUCCESS = "Successful Execution";
    private final CommonComponent component;
    private final AddContactComponent addContactComponent;
    private final FetchContactListComponent fetchContactListComponent;

    @PostMapping()
    public ResponseEntity<Response> addContact(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestBody ContactDto contactDto) {
        addContactComponent.saveOrUpdate(traceId, userId, contactDto);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter-list")
    public ResponseEntity<Response> contactList(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam(defaultValue = "") String searchParam, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Map<String, Object> contactList = fetchContactListComponent.getAllContactList(traceId, page, size, searchParam);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,contactList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter-list/download")
    public ResponseEntity<InputStreamResource> contactListDownload(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam(defaultValue = "") String searchParam, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        System.out.println(userId + " " + traceId + " " + searchParam);
        Map<String, Object> contactList = fetchContactListComponent.getAllContactList(traceId, page, size, searchParam);

        ByteArrayInputStream in = null;
        String fileName = null;
        try {
            in = component.toExcel(searchParam);
            fileName = "contacts.xlsx";
        }catch (Exception e){
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; " + fileName);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/list/{tagName}")
    public ResponseEntity<Response> contactListWithTag(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam(defaultValue = "false") boolean all, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3")
    int size, @PathVariable String tagName) {
        System.out.println(userId + " " + traceId + " " + tagName + " " + all);
        Map<String, Object> contactList = component.getContactList(tagName, traceId, page, size, all);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,contactList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Response> remove(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam List<Long> contactIds) {
        component.deleteContact(traceId, contactIds);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity<Response> tagList(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam(defaultValue = "false") boolean all) {
        System.out.println(userId + " " + traceId + " " + all);
        Map<String, Object> contactList = component.getContactTags(traceId, userId);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,contactList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/tags")
    public ResponseEntity<Response> removeTag(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam Long contactTagId) {
        component.deleteContactTag(traceId, contactTagId);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tags")
    public ResponseEntity<Response> addTag(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestBody ContactTagsDto contactTagsDto) {
        component.addNewTag(traceId, userId, contactTagsDto);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
