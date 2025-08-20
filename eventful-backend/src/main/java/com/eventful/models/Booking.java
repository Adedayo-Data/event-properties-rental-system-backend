package com.eventful.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name="venue_id", nullable = false)
    private Centers venue;
    private LocalDate date;
    private String time;
    private String status;        // e.g., "confirmed", "cancelled"
    // we would create a method in booking controller to change status
    private Double price;
    private int guest;
    private String bookingId;
}

