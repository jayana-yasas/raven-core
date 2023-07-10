package com.example.demo.controller;

import com.example.demo.component.CommonComponent;
import com.example.demo.dto.request.CampaignDto;
import com.example.demo.dto.request.TemplateDto;
import com.example.demo.util.ConsoleOutput;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/campaign")
public class CampaignController {

    private final CommonComponent component;


    @PostMapping()
    public ResponseEntity<Response> campaign(@RequestBody CampaignDto campaignDto, @RequestAttribute String traceId, @RequestAttribute Long userId) {

        ConsoleOutput.print(campaignDto);
        component.addCampaign(traceId, userId, campaignDto);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/template")
    public ResponseEntity<Response> templateAdd(@RequestBody TemplateDto templateDto, @RequestAttribute String traceId, @RequestAttribute Long userId) {

        component.addTemplate(traceId, userId, templateDto);
        Response response = ResponseBuilder.composeSuccessResponse("Successful Execution", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
