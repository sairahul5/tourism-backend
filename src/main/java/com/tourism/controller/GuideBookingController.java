package com.tourism.controller;

import com.tourism.model.GuideBooking;
import com.tourism.service.GuideBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guide-bookings")
@CrossOrigin("*")
public class GuideBookingController {

    @Autowired
    private GuideBookingService service;

    @PostMapping
    public GuideBooking create(@RequestBody GuideBooking gb) {
        return service.create(gb);
    }

    @GetMapping
    public List<GuideBooking> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<GuideBooking> getUserBookings(@PathVariable Long userId) {
        return service.getUserBookings(userId);
    }

    @GetMapping("/guide/{guideId}")
    public List<GuideBooking> getGuideBookings(@PathVariable Long guideId) {
        return service.getGuideBookings(guideId);
    }

    @PutMapping("/{id}/payment")
    public GuideBooking updatePayment(@PathVariable Long id, @RequestParam String utr) {
        return service.updatePayment(id, utr);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id, @RequestParam(required = false) String reason) {
        if(reason != null && !reason.isEmpty()) {
            System.out.println("CRITICAL AUDIT LOG - GuideBooking ID " + id + " was cancelled. Reason provided: " + reason);
        }
        service.deleteBooking(id, reason);
    }
}
