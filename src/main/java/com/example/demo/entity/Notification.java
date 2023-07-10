package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Column(name = "sms_content")
    public String smsContent;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "sender_mask")
    public String senderMask;

    @Column(name = "scheduled_time")
    public LocalDateTime scheduledTime;

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
