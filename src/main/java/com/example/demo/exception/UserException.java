package com.example.demo.exception;

public class UserException extends RuntimeException {

    private Long userId;

    public Long getUserId() {
        return userId;
    }
    public UserException(String message) {
        super(message);
    }
}
