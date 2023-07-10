package com.example.demo.enums;

public enum TelcoEnum {

    MOBITEL("MOBITEL"),
    DIALOG("DIALOG"),
    AIRTEL("AIRTEL"),
    HUTCH("HUTCH");

    String value;

    TelcoEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
