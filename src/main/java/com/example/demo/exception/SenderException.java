package com.example.demo.exception;

public class SenderException extends RuntimeException {

    private Long userId;

    public Long getUserId() {
        return userId;
    }
    public SenderException(String message) {
        super(message);
    }
}
