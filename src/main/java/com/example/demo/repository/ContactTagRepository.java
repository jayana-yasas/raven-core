package com.example.demo.repository;


import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ContactTagRepository extends JpaRepository<ContactTag, Long> {

//    @Modifying
//    @Query(value = "update loan_success_fees_config " +
//            " set is_enabled = false " +
//            " where  investment_type = 3 and loan_id = :loanId ", nativeQuery = true)
//    void deleteByC(Long loanId);

    void deleteByContactIdIn(Collection<Contact> contactIds);

}