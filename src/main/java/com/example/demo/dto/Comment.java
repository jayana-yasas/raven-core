package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Comment {
    private int id;
    private int post_id;
    private String name;
    private String email;
    private String body;
}
