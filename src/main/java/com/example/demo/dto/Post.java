package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Post {
    private int id;
    private int user_id;
    private String title;
    private String body;
}
