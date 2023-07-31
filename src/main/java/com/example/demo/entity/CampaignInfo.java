package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "campaign_info")
public class CampaignInfo extends BaseEntity {

    @Column(name = "campaign_id")
    public Long campaignId;

    @Column(name = "is_valid")
    public Boolean isValid;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "message")
    public String message;

    @Column(name = "status")
    public String status;

    @Column(name = "clicks")
    public Long clicks;

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
