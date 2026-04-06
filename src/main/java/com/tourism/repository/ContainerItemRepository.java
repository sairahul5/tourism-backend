package com.tourism.repository;

import com.tourism.model.ContainerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerItemRepository extends JpaRepository<ContainerItem, Long> {
    List<ContainerItem> findByContainerId(Long containerId);
}
