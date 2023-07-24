package com.example.demo.repository;


import com.example.demo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByName(String name);

    List<Tag> findByNameIn(Collection<String> names);

    List<Tag> findByIdIn(Collection<Long> ids);

    Optional<Tag> findFirstByName(String name);
}