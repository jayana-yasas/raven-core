package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

/*

INSERT INTO `users` (`id`, `created_at`, `updated_at`, `email`, `first_name`, `password`) VALUES ('1', NULL, NULL, 'root', 'Akila', 'password');


*/