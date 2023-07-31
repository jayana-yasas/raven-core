package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "otp_application")
public class OtpApplication extends BaseEntity {

    @Column(name = "identifier")
    public String identifier;

    @Column(name = "name")
    public String name;

    @Column(name = "sender_mask")
    public String senderMask;

    @Column(name = "ttl")
    public Long ttl;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "notification_template_id", referencedColumnName = "id")
    public Template template;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public OtpUser userId;

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
