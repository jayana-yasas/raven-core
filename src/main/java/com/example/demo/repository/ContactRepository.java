package com.example.demo.repository;


import com.example.demo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(nativeQuery = true, value = "select * from contact c where 1=1 AND 1=1 ORDER BY c.updated_at desc LIMIT :size OFFSET :offset ")
    List<Object[]> fetchPaginatedList(int size, int offset);

    @Query(nativeQuery = true, value = "select count(*) from contact c where 1=1 AND 1=1")
    List<BigInteger> fetchCount();

    @Query(nativeQuery = true, value = "select * from contact c where c.user_id=:userId and c.email=:email or c.phone_number=:phoneNumber")
    List<Contact> getListByEmailAndPhoneNumber(Long userId, String email, String phoneNumber);

    long deleteByIdIn(Collection<Long> ids);

    @Query(nativeQuery = true, value = "select c.* from contact_tag ct,contact c where ct.contact_id=c.id AND ct.tag_id=:tadId ORDER BY ct.updated_at desc LIMIT :size OFFSET :offset ")
    List<Object[]> fetchPaginatedListByTagId(long tadId, int size, int offset);

}