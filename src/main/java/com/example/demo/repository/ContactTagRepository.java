package com.example.demo.repository;


import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface ContactTagRepository extends JpaRepository<ContactTag, Long> {
    List<ContactTag> findDistinctByUser_Id(Long id);

    List<ContactTag> findByUser_Id(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from contact_tag where contact_id= :contactId ", nativeQuery = true)
    void deleteByContactId(Long contactId);

    @Transactional
    void deleteByContactIdIn(Collection<Contact> contactIds);

    List<ContactTag> findByTagId_Id(Long id);

    List<ContactTag> findByTagId_IdIn(Collection<Long> ids);

    List<ContactTag> findByContactId_IdAndTagId_IdAndUser_Id(Long id, Long id1, Long id2);

    @Transactional
    @Modifying
    @Query(value = "delete from contact_tag where tag_id= :tagId ", nativeQuery = true)
    void deleteByTagId(Long tagId);

    List<ContactTag> findDistinctByContactId_IdAndUser_Id(Long id, Long id1);


}