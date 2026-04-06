package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "guides")
@Data
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String contact;
    private String location;
    private double pricePerHour;
    private boolean available;
    private String workHours;
    private int dailyLimitHours = 8;
    private String imageUrl;
    
    private boolean isApproved = false;
}
