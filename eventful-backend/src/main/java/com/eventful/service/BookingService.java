package com.eventful.service;

import com.eventful.dto.BookingDTO;
import com.eventful.dto.StatusRequest;
import com.eventful.models.Booking;
import com.eventful.models.Centers;
import com.eventful.models.UserPrincipal;
import com.eventful.models.Users;
import com.eventful.repository.BookingRepository;
import com.eventful.repository.CentersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    CentersRepository centersRepo;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        System.out.println("Booking data Received: "+ bookingDTO.toString());
        Booking booking = new Booking();
        // find venue by id
        Optional<Centers> optCenter = centersRepo.findById(bookingDTO.getVenue());
        if(optCenter.isEmpty()){
            return null;
        }
        Centers venue = optCenter.get();
        System.out.println("Venue gotten from db: " + venue.getName());
        // get User object
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authenticationObj.getPrincipal();
        Users users = userPrincipal.getUser(); // user object inside users variable

        booking.setUser(users);
        booking.setVenue(venue);
        booking.setDate(LocalDate.parse(bookingDTO.getDate()));
        booking.setTime(bookingDTO.getTime());
        booking.setPrice(booking.getPrice());
        booking.setGuest(bookingDTO.getGuests());
        booking.setStatus("pending"); // confirmed, pending, cancelled, completed
        booking.setBookingId(generateBookingId());
        return bookingRepository.save(booking);
    }

    public Optional<Booking> updateBooking(Long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(existing -> {
            existing.setDate(updatedBooking.getDate());
            existing.setStatus(updatedBooking.getStatus());
            return bookingRepository.save(existing);
        });
    }

    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String generateBookingId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomPart = (int) (Math.random() * 9000) + 1000; // random 4-digit number
        return "BK-" + datePart + "-" + randomPart;
    }

    public List<Booking> getBookingsForUser(Long userId, String status){

        List<Booking> bookings;

        if (status != null && !status.isEmpty()) {
            bookings = bookingRepository.findByUserIdAndStatus(userId, status);
        } else {
            bookings = bookingRepository.findByUserId(userId);
        }

        return bookings;
    }

    public Booking updateBookingStatus(Long id, StatusRequest request) {
        Optional<Booking> optBooking = bookingRepository.findById(id);
        if(optBooking.isEmpty()){
            return null;
        }
        Booking booking = optBooking.get();

        booking.setStatus(request.getStatus());

        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }
}

