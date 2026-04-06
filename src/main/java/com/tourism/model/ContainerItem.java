package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "container_items")
@Data
public class ContainerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private Container container;

    private Long referenceId; // ID of the Homestay or Place
    private String type; // HOMESTAY / PLACE
}
