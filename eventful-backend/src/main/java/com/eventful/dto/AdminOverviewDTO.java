package com.eventful.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminOverviewDTO {
    private long totalUsers;
    private long totalBookings;
    private double totalRevenue;
}

