package com.example.demo.repository;


import com.example.demo.entity.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
    Segment findByName(String name);

}