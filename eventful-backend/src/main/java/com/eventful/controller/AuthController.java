package com.eventful.controller;

import com.eventful.dto.AuthMessage;
import com.eventful.dto.LoginRequest;
import com.eventful.dto.RegisterDTO;
import com.eventful.models.Users;
import com.eventful.repository.UserRepository;
import com.eventful.security.JwtService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    // sign up controller
    @PostMapping("/register")
    public ResponseEntity<AuthMessage> register(@RequestBody RegisterDTO registerDTO){
        System.out.println("controller hit!");
        // convert dto object to Entity object
        Users user = new Users();
        user.setFullname(registerDTO.getFullname());
        user.setEmail(registerDTO.getEmail());
//        user.setRole(registerDTO.getRole());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // save user
        System.out.println("User role: " + user.getRole().toString());
        Users savedUser = userRepo.save(user);

        // Add user to Security
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate token
        String jwt = jwtService.generateToken(savedUser);

        // user role
        String role =  savedUser.getRole().toString();

        // Log success message
        String message = "Sign-up Successful";

        return ResponseEntity.ok(new AuthMessage(jwt, role, message));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // find user from db
       Users user = userRepo.findByEmail(username);

       if(user == null){
           throw new UsernameNotFoundException("Username not found!");
       }

       if(passwordEncoder.matches(password, user.getPassword())){
           // generate JWT
           String jwt = jwtService.generateToken(user);
           String message = "Login successful";
           return ResponseEntity.ok(new AuthMessage(jwt, user.getRole().name(), message));
       }
        return null;
    }

//    @GetMapping("/me")
//    public ResponseEntity<RegisterDTO> currentLoggedInUser(@RequestHeader("Authorization") String token){
//        String username = jwtService.extractUsername(token);
//        //find the username from the database
//        Users user = userRepo.findByEmail(username);
//
//        if(user == null){
//            throw new UsernameNotFoundException("Username not found!");
//        }
//
//        if(Security)
//    }
}
