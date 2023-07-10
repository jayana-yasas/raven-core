package com.example.demo.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ApiResponseSkeleton {

    ObjectMapper mapper = new ObjectMapper();
    HttpStatus httpStatus;

    public ApiResponse(HttpStatus httpStatus, Boolean success, T data, String message) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        setHttpCode(httpStatus);
        setSuccess(success);
        setMessage(message);
        setData(mapper.valueToTree(data));
        this.httpStatus = httpStatus;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public ResponseEntity getResponse(T apiResponse) {
        try {
            mapper.writeValueAsString(apiResponse);
            return new ResponseEntity<String>(mapper.writeValueAsString(apiResponse), httpStatus);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
