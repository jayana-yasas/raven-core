package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsoleOutput {

    public static void print(Object obj) {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(obj));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
