package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "template")
public class Template extends BaseEntity {

    @Column(name = "name")
    public String name;

    @Column(name = "template_type")
    public String templateType; //SMS//EMAIL//BOTH

    @Column(name = "template_sms")
    public String templateSms;

    @Column(name = "template_email")
    public String templateEmail;

    @Column(name = "user_id")
    public Long userId;

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
