package com.tourism.controller;

import com.tourism.model.Booking;
import com.tourism.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService service;

    @PostMapping
    public Booking create(@RequestBody Booking b) {
        return service.createBooking(b);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return service.getUserBookings(userId);
    }

    @GetMapping("/homestay/{homestayId}")
    public List<Booking> getHomestayBookings(@PathVariable Long homestayId) {
        return service.getHomestayBookings(homestayId);
    }

    @GetMapping("/host/{hostId}")
    public List<Booking> getHostBookings(@PathVariable Long hostId) {
        return service.getHostBookings(hostId);
    }

    @PutMapping("/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}
