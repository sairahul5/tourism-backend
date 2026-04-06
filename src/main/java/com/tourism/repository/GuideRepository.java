package com.tourism.repository;

import com.tourism.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {

    List<Guide> findByIsApprovedFalse();
    Guide findByUserId(Long userId);

    @Query("SELECT g FROM Guide g WHERE g.isApproved = true " +
           "AND (:location = '' OR LOWER(g.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (g.pricePerHour >= :minPrice) " +
           "AND (g.pricePerHour <= :maxPrice)")
    List<Guide> findFilteredGuides(
        @Param("location") String location,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice
    );
}
