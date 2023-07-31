package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sender")
public class Sender extends BaseEntity {

    @Column(name = "status")
    public String status;

    @Column(name = "sender_type")
    public String senderType;

    @Column(name = "sender_email")
    public String senderEmail;

    @Column(name = "sender_name")
    public String senderName;

    @Column(name = "sender_phone")
    public String senderPhone;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "is_idd_enabled")
    public Boolean isIddEnabled;

    @Column(name = "purpose")
    public String purpose;

    @Column(name = "sending_as_type")
    public String sendingAsType;

    @Column(name = "businessLegalName")
    public String businessLegalName;

    @Column(name = "businessType")
    public String businessType;

    @Column(name = "authorizedPersonName")
    public String authorizedPersonName;

    @Column(name = "authorizedPersonDesignation")
    public String authorizedPersonDesignation;

    @Column(name = "fullName")
    public String fullName;

    @Column(name = "legalDocumentType")
    public String legalDocumentType;

    @Column(name = "legalDocumentId")
    public String legalDocumentId;

    @PrePersist
    private void prePersist() {
        if (getCreatedAt() == null) setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}
