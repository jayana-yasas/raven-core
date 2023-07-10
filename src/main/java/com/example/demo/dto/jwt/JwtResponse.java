package com.example.demo.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private final String accessToken;

}
