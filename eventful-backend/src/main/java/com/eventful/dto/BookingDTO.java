package com.eventful.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {

    private Long venue;
//    private String user;
//    private String userEmail;
    private String date;
    private String time;
//    private String status;
    private Double amount;
    private int guests;
}
