package com.tourism.repository;

import com.tourism.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    List<Container> findByType(String type);
}
