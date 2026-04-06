package com.tourism.repository;

import com.tourism.model.GuideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideBookingRepository extends JpaRepository<GuideBooking, Long> {
    List<GuideBooking> findByUserId(Long userId);
    List<GuideBooking> findByGuideId(Long guideId);
    List<GuideBooking> findByGuideIdAndDate(Long guideId, java.time.LocalDate date);
}
