package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "short_url_info")
public class ShortUrlInfo extends BaseEntity {

    @Column(name = "campaign_id")
    public Long campaignId;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "key_value")
    public String key;

    @Column(name = "path")
    public String path;

    @Column(name = "original_url")
    public String originalUrl;

    @Column(name = "short_url")
    public String shortUrl;

    @Column(name = "link_id")
    public String idString;

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
