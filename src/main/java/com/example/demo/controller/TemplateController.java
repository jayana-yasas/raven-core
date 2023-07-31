package com.example.demo.controller;

import com.example.demo.component.AddSenderComponent;
import com.example.demo.component.DeleteSenderComponent;
import com.example.demo.component.FetchSenderListComponent;
import com.example.demo.dto.request.SenderDto;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/template")
public class TemplateController {
    private static final String RESPONSE_SUCCESS = "Successful Execution";
    private final DeleteSenderComponent deleteSenderComponent;
    private final AddSenderComponent addSenderComponent;
    private final FetchSenderListComponent fetchSenderListComponent;

    @PostMapping()
    public ResponseEntity<Response> addTemplate(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestBody SenderDto senderDto) {
        addSenderComponent.saveOrUpdate(traceId, userId, senderDto);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter-list")
    public ResponseEntity<Response> templateList(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam(defaultValue = "") String searchParam, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Map<String, Object> senderList = fetchSenderListComponent.getAllSenderList(traceId, page, size, searchParam, userId);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS, senderList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Response> remove(@RequestAttribute String traceId, @RequestAttribute Long userId, @RequestParam Long senderId) {
        deleteSenderComponent.deleteSender(traceId,userId, senderId);
        Response response = ResponseBuilder.composeSuccessResponse(RESPONSE_SUCCESS,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
