package com.tourism.repository;

import com.tourism.model.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    List<Homestay> findByStateAndDistrict(String state, String district);
    List<Homestay> findByHostId(Long hostId);
    List<Homestay> findByIsApprovedFalse();

    @Query("SELECT h FROM Homestay h WHERE h.isApproved = true " +
           "AND (:state = '' OR LOWER(h.state) LIKE LOWER(CONCAT('%', :state, '%'))) " +
           "AND (:district = '' OR LOWER(h.district) LIKE LOWER(CONCAT('%', :district, '%'))) " +
           "AND (h.price >= :minPrice) " +
           "AND (h.price <= :maxPrice)")
    List<Homestay> findFilteredHomestays(
        @Param("state") String state,
        @Param("district") String district,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice
    );
}
