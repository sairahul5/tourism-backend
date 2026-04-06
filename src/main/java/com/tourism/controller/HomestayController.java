package com.tourism.controller;

import com.tourism.model.Homestay;
import com.tourism.service.HomestayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homestays")
@CrossOrigin("*")
public class HomestayController {

    @Autowired
    private HomestayService service;

    @PostMapping
    public Homestay add(@RequestBody Homestay h) {
        return service.add(h);
    }

    @GetMapping
    public List<Homestay> getAll(
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String district,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        return service.getAll(state, district, minPrice, maxPrice);
    }

    @GetMapping("/pending")
    public List<Homestay> getPending() {
        return service.getPendingHomestays();
    }

    @PutMapping("/{id}/approve")
    public Homestay approve(@PathVariable Long id) {
        return service.approveHomestay(id);
    }

    @GetMapping("/{id}")
    public Homestay getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/host/{hostId}")
    public List<Homestay> getByHostId(@PathVariable Long hostId) {
        return service.getByHostId(hostId);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Homestay update(@PathVariable Long id, @RequestBody Homestay h) {
        return service.update(id, h);
    }
}
