package com.eventful.dto;

import com.eventful.models.ROLE;
import lombok.Data;

@Data
public class RegisterDTO {

    private String fullname;
    private String email;
    private String password;
//    private ROLE role;
}
