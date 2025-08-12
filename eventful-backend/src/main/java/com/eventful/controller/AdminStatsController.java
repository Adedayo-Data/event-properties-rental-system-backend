package com.eventful.controller;

import com.eventful.service.AdminStatsService;
import com.eventful.dto.AdminOverviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminStatsController {

    @Autowired
    private AdminStatsService statsService;

    // GET /api/admin/overview
    @GetMapping("/overview")
    public ResponseEntity<AdminOverviewDTO> getAdminOverview() {
        return ResponseEntity.ok(statsService.getOverview());
    }

    // GET /api/admin/bookings/today
    @GetMapping("/bookings/today")
    public ResponseEntity<Integer> getTodayBookings() {
        return ResponseEntity.ok(statsService.getTodayBookingsCount());
    }

    // GET /api/admin/revenue/monthly
    @GetMapping("/revenue/monthly")
    public ResponseEntity<?> getMonthlyRevenue() {
        return ResponseEntity.ok(statsService.getMonthlyRevenue());
    }
}

