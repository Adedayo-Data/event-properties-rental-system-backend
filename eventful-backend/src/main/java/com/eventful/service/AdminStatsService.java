package com.eventful.service;

import com.eventful.models.Booking;
import com.eventful.models.Centers;
import com.eventful.repository.BookingRepository;
import com.eventful.repository.CentersRepository;
import com.eventful.repository.UserRepository;
import com.eventful.dto.AdminOverviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminStatsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CentersRepository centersRepository;

    // GET /api/admin/overview
    public AdminOverviewDTO getOverview() {
        long totalUsers = userRepository.count();
        long totalBookings = bookingRepository.count();
        double totalRevenue = bookingRepository.findAll().stream()
                .mapToDouble(this::calculateBookingRevenue)
                .sum();

        return new AdminOverviewDTO(totalUsers, totalBookings, totalRevenue);
    }

    // GET /api/admin/bookings/today
    public int getTodayBookingsCount() {
        LocalDate today = LocalDate.now();
        return (int) bookingRepository.findAll().stream()
                .filter(b -> b.getBookingDate().equals(today))
                .count();
    }

    // GET /api/admin/revenue/monthly
    public Map<String, Double> getMonthlyRevenue() {
        Map<String, Double> revenueMap = new LinkedHashMap<>();

        List<Booking> bookings = bookingRepository.findAll();

        for (int i = 0; i < 6; i++) {
            YearMonth month = YearMonth.now().minusMonths(i);
            double monthlyRevenue = bookings.stream()
                    .filter(b -> YearMonth.from(b.getBookingDate()).equals(month))
                    .mapToDouble(this::calculateBookingRevenue)
                    .sum();
            revenueMap.put(month.toString(), monthlyRevenue);
        }

        return revenueMap;
    }

    // Helper method
    private double calculateBookingRevenue(Booking booking) {
        Optional<Centers> center = centersRepository.findById(booking.getCenterId());
        return center.map(Centers::getPrice).orElse(0.0);
    }
}

