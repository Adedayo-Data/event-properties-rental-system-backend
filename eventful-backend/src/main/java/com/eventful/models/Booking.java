package com.eventful.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;          // You may want to map this to a User entity later
    private Long centerId;        // You can also map this to Centers entity if needed
    private LocalDate bookingDate;
    private String status;        // e.g., "confirmed", "cancelled"
}

