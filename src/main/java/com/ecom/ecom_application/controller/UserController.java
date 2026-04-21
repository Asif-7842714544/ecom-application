package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.domain.User;
import com.ecom.ecom_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        
       return  ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.addUser(user));
    }

    @GetMapping("/api/users/{userId}")
    public User getAllUsers(@PathVariable Long userId)
    {
        return userService.getUserbyId(userId);
   }


}
