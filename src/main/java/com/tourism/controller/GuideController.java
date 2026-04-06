package com.tourism.controller;

import com.tourism.model.Guide;
import com.tourism.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guides")
@CrossOrigin("*")
public class GuideController {

    @Autowired
    private GuideService service;

    @PostMapping
    public Guide create(@RequestBody Guide g) {
        return service.create(g);
    }

    @GetMapping
    public List<Guide> getAll(
        @RequestParam(required = false) String location,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        return service.getAll(location, minPrice, maxPrice);
    }

    @GetMapping("/pending")
    public List<Guide> getPending() {
        return service.getPendingGuides();
    }

    @PutMapping("/{id}/approve")
    public Guide approve(@PathVariable Long id) {
        return service.approveGuide(id);
    }

    @PutMapping("/{id}/availability")
    public Guide updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
        return service.updateAvailability(id, available);
    }

    @GetMapping("/user/{userId}")
    public Guide getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/{id}")
    public Guide updateGuide(@PathVariable Long id, @RequestBody Guide g) {
        return service.update(id, g);
    }
}
