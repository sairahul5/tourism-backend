package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "containers")
@Data
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type; // HOME_SECTION / FEATURED / NEARBY

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User admin;
}
