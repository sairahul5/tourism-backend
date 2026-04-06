package com.tourism.service;

import com.tourism.model.Homestay;
import com.tourism.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomestayService {

    @Autowired
    private HomestayRepository repo;

    public Homestay add(Homestay h) {
        return repo.save(h);
    }

    public List<Homestay> getAll(String state, String district, Double minPrice, Double maxPrice) {
        if (state == null) state = "";
        if (district == null) district = "";
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = 9999999.0;
        return repo.findFilteredHomestays(state, district, minPrice, maxPrice);
    }

    public List<Homestay> getPendingHomestays() {
        return repo.findByIsApprovedFalse();
    }

    public Homestay approveHomestay(Long id) {
        Homestay h = repo.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        h.setApproved(true);
        return repo.save(h);
    }

    public Homestay getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
    }

    public List<Homestay> getByHostId(Long hostId) {
        return repo.findByHostId(hostId);
    }

    @Autowired
    private com.tourism.repository.BookingRepository bookingRepo;

    public void delete(Long id) {
        bookingRepo.deleteAll(bookingRepo.findByHomestayId(id));
        repo.deleteById(id);
    }

    public Homestay update(Long id, Homestay updateRequest) {
        Homestay existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        existing.setName(updateRequest.getName());
        existing.setContactNumber(updateRequest.getContactNumber());
        existing.setEmail(updateRequest.getEmail());
        existing.setState(updateRequest.getState());
        existing.setDistrict(updateRequest.getDistrict());
        existing.setPrice(updateRequest.getPrice());
        existing.setRooms(updateRequest.getRooms());
        existing.setDescription(updateRequest.getDescription());
        if(updateRequest.getImageUrl() != null && !updateRequest.getImageUrl().isEmpty()) {
            existing.setImageUrl(updateRequest.getImageUrl());
        }
        existing.setApproved(false); // Force re-approval linearly upon modification
        return repo.save(existing);
    }
}
