package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "homestay_images")
@Data
public class HomestayImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    private String imageUrl;
    private String type; // ROOM / PLACE / NATURE
}
