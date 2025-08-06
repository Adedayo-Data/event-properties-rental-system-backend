package com.eventful.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {

    @Value("${jwt-key}")
    public String JWTKEY;

    public static final String HEADER = "Authorization";
}
