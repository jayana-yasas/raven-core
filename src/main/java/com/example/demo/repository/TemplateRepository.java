package com.example.demo.repository;


import com.example.demo.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByNameAndTemplateType(String name, String templateType);

    Optional<Template> findByNameAndTemplateTypeAndUserId(String name, String templateType, Long userId);


}