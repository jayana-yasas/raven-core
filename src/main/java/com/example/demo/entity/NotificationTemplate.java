package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "notification_template")
public class NotificationTemplate extends BaseEntity {

    @Column(name = "name")
    public String name;

    @Column(name = "template_type")
    public String templateType; //SMS//EMAIL//BOTH

    @Column(name = "templates_sms")
    public String templatesSms;

    @Column(name = "templates_email")
    public String templatesEmail;

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
