package com.example.demo.util;

import java.sql.Timestamp;

public class ResponseBuilder {
    public ResponseBuilder() {
    }

    public static Response composeFailureResponse(String errorMessage) {
        return composeResponse(ResponseStatus.FAILURE, errorMessage, null);
    }

    public static Response composeFailureResponse(String errorMessage, Object data) {
        return composeResponse(ResponseStatus.FAILURE, errorMessage, data);
    }

    public static Response composeSuccessResponse(String message) {
        return composeResponse(ResponseStatus.SUCCESS, message, null);
    }

    public static Response composeSuccessResponse(String message, Object data) {
        return composeResponse(ResponseStatus.SUCCESS, message, data);
    }

    private static Response composeResponse(ResponseStatus responseStatus, String message, Object data) {
        return Response.builder().status(responseStatus).message(message).responseTimestamp(getCurrentTimestamp()).data(data).build();
    }

    private static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
