package com.tourism.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "guide_bookings")
@Data
public class GuideBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int hours;
    private String status = "ACTIVE"; // ACTIVE / CANCELLED
    private String cancellationReason;
    private String paymentStatus; // PENDING / PAID
    private String utrNumber;
}
