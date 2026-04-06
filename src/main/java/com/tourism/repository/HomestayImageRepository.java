package com.tourism.repository;

import com.tourism.model.HomestayImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayImageRepository extends JpaRepository<HomestayImage, Long> {
    List<HomestayImage> findByHomestayId(Long homestayId);
}
