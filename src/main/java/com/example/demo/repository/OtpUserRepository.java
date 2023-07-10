package com.example.demo.repository;


import com.example.demo.entity.OtpUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtpUserRepository extends JpaRepository<OtpUser, Long> {

    //    select * from otp_user where email=:email
    List<OtpUser> findAllByEmail(String email);
}