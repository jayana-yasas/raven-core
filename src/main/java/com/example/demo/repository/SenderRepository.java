package com.example.demo.repository;


import com.example.demo.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface SenderRepository extends JpaRepository<Sender, Long> {

    boolean existsByUserIdAndSenderName(Long userId, String senderName);

    boolean existsByUserIdAndId(Long userId, Long id);

    Optional<Sender> findByUserIdAndSenderName(Long userId, String senderName);

    Optional<Sender> findByUserIdAndId(Long userId, Long id);

    boolean existsByUserIdAndSenderEmail(Long userId, String senderEmail);

    boolean existsByUserIdAndSenderPhone(Long userId, String senderPhone);

    @Query(nativeQuery = true, value = "select count(*) from sender s where s.user_id=:userId and s.sender_type =:senderType")
    List<BigInteger> fetchCount(String senderType, Long userId);

    @Query(nativeQuery = true, value = "select * from sender s where s.user_id=:userId and s.sender_type =:senderType ORDER BY s.updated_at desc LIMIT :size OFFSET :offset")
    List<Sender> fetchPaginatedList(String senderType, int size, int offset, Long userId);





}