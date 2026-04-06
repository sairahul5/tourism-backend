package com.tourism.controller;

import com.tourism.model.Container;
import com.tourism.model.ContainerItem;
import com.tourism.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
@CrossOrigin("*")
public class ContainerController {

    @Autowired
    private ContainerService service;

    @GetMapping
    public List<Container> getAll() {
        return service.getAllContainers();
    }

    @GetMapping("/{id}/items")
    public List<ContainerItem> getItems(@PathVariable Long id) {
        return service.getItemsByContainerId(id);
    }

    @PostMapping
    public Container createContainer(@RequestBody Container c) {
        return service.createContainer(c);
    }

    @PostMapping("/{id}/items")
    public ContainerItem addItem(@PathVariable Long id, @RequestBody ContainerItem item) {
        return service.addItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteContainer(@PathVariable Long id) {
        service.deleteContainer(id);
    }

    @PutMapping("/{id}")
    public Container updateContainer(@PathVariable Long id, @RequestBody Container request) {
        return service.updateContainer(id, request);
    }
}
