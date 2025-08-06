package com.eventful.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthMessage {

    private String jwt;
    private String role;
    private String message;
}
