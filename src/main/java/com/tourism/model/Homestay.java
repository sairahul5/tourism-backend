package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "homestays")
@Data
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    private String name;
    private String contactNumber;
    private String email;
    private String state;
    private String district;
    private double price;
    private int rooms;
    
    @Column(length = 2000)
    private String description;

    private boolean isApproved = false;
    private String imageUrl;
}
