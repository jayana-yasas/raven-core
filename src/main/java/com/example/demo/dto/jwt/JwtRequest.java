package com.example.demo.dto.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@NoArgsConstructor
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
