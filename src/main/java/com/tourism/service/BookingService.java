package com.tourism.service;

import com.tourism.model.Booking;
import com.tourism.model.Homestay;
import com.tourism.repository.BookingRepository;
import com.tourism.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repo;
    
    @Autowired
    private HomestayRepository homestayRepo;

    public Booking createBooking(Booking b) {
        if (repo.existsByHomestayIdAndUserIdAndDate(b.getHomestay().getId(), b.getUser().getId(), b.getDate())) {
            throw new RuntimeException("Duplicate Booking: You have already reserved a room at this property for this date.");
        }
        
        Homestay homestay = homestayRepo.findById(b.getHomestay().getId())
                            .orElseThrow(() -> new RuntimeException("Homestay not found"));
                            
        if (homestay.getRooms() < b.getRoomsBooked()) {
            throw new RuntimeException("Inventory Error: Not enough rooms currently available for this homestay.");
        }
        
        // Deduct inventory instantly
        homestay.setRooms(homestay.getRooms() - b.getRoomsBooked());
        homestayRepo.save(homestay);
        
        b.setStatus("PENDING");
        return repo.save(b);
    }

    public List<Booking> getUserBookings(Long userId) {
        return repo.findByUserId(userId);
    }

    public List<Booking> getAllBookings() {
        return repo.findAll();
    }

    public List<Booking> getHomestayBookings(Long homestayId) {
        return repo.findByHomestayId(homestayId);
    }

    public List<Booking> getHostBookings(Long hostId) {
        return repo.findByHomestayHostId(hostId);
    }

    public Booking updateStatus(Long id, String status) {
        Booking b = repo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus(status);
        return repo.save(b);
    }
}
