package com.eventful.controller;

import com.eventful.dto.RegisterDTO;
import com.eventful.models.Users;
import com.eventful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> allUsers = userRepo.findAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        // find from userRepo by Id
        Optional<Users> optUser = userRepo.findById(id);
        if(optUser.isEmpty()){
            return null;
        }

        Users users = optUser.get();

        return ResponseEntity.ok(users);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Users> updateUser(@PathVariable Long id,
//                                            @RequestBody RegisterDTO){
//
//        Optional<Users> optUser = userRepo.findById(id);
//
//
//    }

}
