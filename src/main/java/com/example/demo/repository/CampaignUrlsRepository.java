package com.example.demo.repository;


import com.example.demo.entity.CampaignTag;
import com.example.demo.entity.CampaignUrls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignUrlsRepository extends JpaRepository<CampaignUrls, Long> {


}