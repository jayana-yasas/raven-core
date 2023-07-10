package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

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
