package com.example.demo.repository;


import com.example.demo.entity.OtpDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtpDetailRepository extends JpaRepository<OtpDetails, Long> {

    List<OtpDetails> findByTypeAndEmailAndOtpOrderByCreatedAtDesc(String type, String email, Integer otp);

    boolean existsByTypeAndEmailAndOtp(String type, String email, Integer otp);

    boolean existsByTypeAndPhoneNumberAndOtp(String type, String phoneNumber, Integer otp);


}