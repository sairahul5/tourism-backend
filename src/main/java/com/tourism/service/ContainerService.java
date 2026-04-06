package com.tourism.service;

import com.tourism.model.Container;
import com.tourism.model.ContainerItem;
import com.tourism.repository.ContainerRepository;
import com.tourism.repository.ContainerItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerService {

    @Autowired
    private ContainerRepository containerRepo;

    @Autowired
    private ContainerItemRepository itemRepo;

    public List<Container> getAllContainers() {
        return containerRepo.findAll();
    }

    public List<ContainerItem> getItemsByContainerId(Long id) {
        return itemRepo.findByContainerId(id);
    }

    public Container createContainer(Container c) {
        return containerRepo.save(c);
    }

    public ContainerItem addItem(Long id, ContainerItem item) {
        Container container = containerRepo.findById(id).orElseThrow(() -> new RuntimeException("Container not found"));
        item.setContainer(container);
        return itemRepo.save(item);
    }

    public void deleteContainer(Long id) {
        List<ContainerItem> items = itemRepo.findByContainerId(id);
        itemRepo.deleteAll(items);
        containerRepo.deleteById(id);
    }

    public Container updateContainer(Long id, Container req) {
        Container existing = containerRepo.findById(id).orElseThrow(() -> new RuntimeException("Container not found"));
        existing.setTitle(req.getTitle());
        existing.setType(req.getType());
        return containerRepo.save(existing);
    }
}
