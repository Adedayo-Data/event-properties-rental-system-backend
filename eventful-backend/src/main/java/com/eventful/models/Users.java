package com.eventful.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private int totalBookings;
    private Double totalSpent;
    private String Status = "active";

    @CreatedDate
    private String joinDate;
    private ROLE role = ROLE.USER;

    @CreatedDate
    private LocalDate createdAt;
}
