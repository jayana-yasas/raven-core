package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
//@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new User(
                userId,
                userId,
                new ArrayList<>());

    }
}

