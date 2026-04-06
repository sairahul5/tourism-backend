package com.tourism.repository;

import com.tourism.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByHomestayId(Long homestayId);
    List<Booking> findByHomestayHostId(Long hostId);
    boolean existsByHomestayIdAndUserIdAndDate(Long homestayId, Long userId, LocalDate date);
}
