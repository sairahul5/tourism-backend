package com.tourism.service;

import com.tourism.model.GuideBooking;
import com.tourism.repository.GuideBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideBookingService {

    @Autowired
    private GuideBookingRepository repo;

    @Autowired
    private com.tourism.repository.GuideRepository guideRepo;

    public GuideBooking create(GuideBooking gb) {
        com.tourism.model.Guide guide = guideRepo.findById(gb.getGuide().getId()).orElseThrow(() -> new RuntimeException("Guide not found"));
        
        if (guide.getUser() != null && gb.getUser() != null && guide.getUser().getId().equals(gb.getUser().getId())) {
            throw new RuntimeException("Guides cannot book themselves.");
        }

        if (gb.getDate() == null || gb.getHours() <= 0) {
            throw new RuntimeException("Invalid date or hours requested.");
        }

        int limit = guide.getDailyLimitHours();
        if (limit <= 0) limit = 8; // Default limit if unset

        List<GuideBooking> dayBookings = repo.findByGuideIdAndDate(guide.getId(), gb.getDate());
        int bookedHours = 0;
        if (dayBookings != null) {
            bookedHours = dayBookings.stream()
                    .filter(b -> !"CANCELLED".equals(b.getStatus()))
                    .mapToInt(GuideBooking::getHours).sum();
        }

        if (bookedHours + gb.getHours() > limit) {
            throw new RuntimeException("Booking impossible! Guide only has " + (limit - bookedHours) + " hours left on this date.");
        }

        gb.setPaymentStatus("PENDING");
        return repo.save(gb);
    }

    public List<GuideBooking> getUserBookings(Long userId) {
        return repo.findByUserId(userId);
    }

    public List<GuideBooking> getAllBookings() {
        return repo.findAll();
    }

    public List<GuideBooking> getGuideBookings(Long guideId) {
        return repo.findByGuideId(guideId);
    }

    public GuideBooking updatePayment(Long id, String utr) {
        GuideBooking gb = repo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        gb.setUtrNumber(utr);
        gb.setPaymentStatus("PAID");
        return repo.save(gb);
    }

    public void deleteBooking(Long id, String reason) {
        GuideBooking gb = repo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        gb.setStatus("CANCELLED");
        gb.setPaymentStatus("CANCELLED");
        gb.setCancellationReason(reason);
        repo.save(gb);
    }
}
