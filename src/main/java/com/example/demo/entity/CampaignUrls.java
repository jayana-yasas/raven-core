package com.example.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "campaign_urls")
public class CampaignUrls extends BaseEntity {

    @Column(name = "campaign_id")
    public Long campaignId;

    @Column(name = "param")
    public String param;

    @Column(name = "original_url")
    public String originalURL;
}
