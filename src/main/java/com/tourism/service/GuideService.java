package com.tourism.service;

import com.tourism.model.Guide;
import com.tourism.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {

    @Autowired
    private GuideRepository repo;

    public Guide create(Guide g) {
        return repo.save(g);
    }

    public List<Guide> getAll(String location, Double minPrice, Double maxPrice) {
        if (location == null) location = "";
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = 9999999.0;
        return repo.findFilteredGuides(location, minPrice, maxPrice);
    }

    public List<Guide> getPendingGuides() {
        return repo.findByIsApprovedFalse();
    }

    public Guide approveGuide(Long id) {
        Guide g = repo.findById(id).orElseThrow(() -> new RuntimeException("Guide not found"));
        g.setApproved(true);
        return repo.save(g);
    }

    public Guide updateAvailability(Long id, boolean available) {
        Guide g = repo.findById(id).orElseThrow(() -> new RuntimeException("Guide not found"));
        g.setAvailable(available);
        return repo.save(g);
    }

    public Guide update(Long id, Guide req) {
        Guide existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Guide not found"));
        existing.setName(req.getName());
        existing.setContact(req.getContact());
        existing.setLocation(req.getLocation());
        existing.setPricePerHour(req.getPricePerHour());
        existing.setWorkHours(req.getWorkHours());
        existing.setDailyLimitHours(req.getDailyLimitHours());
        if(req.getImageUrl() != null && !req.getImageUrl().isEmpty()) {
            existing.setImageUrl(req.getImageUrl());
        }
        existing.setApproved(false); // Force Re-approval from Admin
        return repo.save(existing);
    }

    public Guide getByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
