package com.example.demo.repository;


import com.example.demo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(nativeQuery = true, value = "select * from contact c where c.user_id=:userId and (c.email like %:searchParam% or c.phone_number like %:searchParam% or c.name like %:searchParam%)  ORDER BY c.updated_at desc LIMIT :size OFFSET :offset")
    List<Contact> fetchPaginatedList(String searchParam, int size, int offset, Long userId);

    @Query(nativeQuery = true, value = "select * from contact c where c.user_id=:userId and (c.email like %:searchParam% or c.phone_number like %:searchParam% or c.name like %:searchParam%)  ORDER BY c.updated_at desc")
    List<Contact> fetchList(String searchParam, Long userId);

    @Query(nativeQuery = true, value = "select count(*) from contact c where c.user_id=:userId and (c.email like %:searchParam% or c.phone_number like %:searchParam% or c.name like %:searchParam%)")
    List<BigInteger> fetchCount(String searchParam, Long userId);

    boolean existsByUserIdAndEmail(Long userId, String email);

    boolean existsByUserIdAndPhoneNumber(Long userId, String phoneNumber);

    Optional<Contact> findByUserIdAndEmailOrPhoneNumber(Long userId, String email, String phoneNumber);

    long deleteByIdIn(Collection<Long> ids);

    @Query(nativeQuery = true, value = "select c.* from contact_tag ct,contact c where ct.contact_id=c.id AND ct.tag_id=:tadId ORDER BY ct.updated_at desc LIMIT :size OFFSET :offset ")
    List<Object[]> fetchPaginatedListByTagId(long tadId, int size, int offset);

}