package com.eventful.controller;

import com.eventful.dto.BookingDTO;
import com.eventful.dto.StatusRequest;
import com.eventful.models.Booking;
import com.eventful.models.UserPrincipal;
import com.eventful.models.Users;
import com.eventful.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // GET /api/bookings (Admin Only)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // GET /api/bookings/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    // POST /api/bookings
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking created = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(created);
    }

    // PUT /api/bookings/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/bookings/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(@RequestParam(required = false) String status){
//        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User Principal is: "+ userPrincipal.getUsername());
        Users users = userPrincipal.getUser();
        return ResponseEntity.ok(bookingService.getBookingsForUser(users.getId(), status));

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Long id,
                                                       @RequestBody StatusRequest request){
        Booking booking = bookingService.updateBookingStatus(id, request);
        if(booking==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(booking);
    }

}

