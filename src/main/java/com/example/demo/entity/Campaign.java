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
@Table(name = "campaign")
public class Campaign extends BaseEntity {

    @Column(name = "type")
    public String type;

    @Column(name = "name")
    public String name;

    @Column(name = "sender")
    public String sender;

    @Column(name = "is_scheduled")
    public Boolean isScheduled;

    @Column(name = "scheduled_datetime")
    public LocalDateTime scheduledDatetime;

    @Column(name = "template")
    public String template;

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
