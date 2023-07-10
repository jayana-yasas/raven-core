package com.example.demo.enums;

public enum OptRequestType {
    OTP("OTP"),
    VERIFY("VERIFY"),
    ;

    private String displayStatus;

    OptRequestType(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String displayStatus() {
        return displayStatus;
    }

    @Override
    public String toString() {
        return displayStatus;
    }
}
