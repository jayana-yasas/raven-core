package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static final List<String> OTP_TEMPLATE_PLACEHOLDERS = new ArrayList<String>() {
        {
            add("<otp>");
            add("<otp_for>");
        } };
}
