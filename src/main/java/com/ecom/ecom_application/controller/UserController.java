package com.ecom.ecom_application.controller;

import com.ecom.ecom_application.domain.User;
import com.ecom.ecom_application.dto.UserRequest;
import com.ecom.ecom_application.dto.UserResponse;
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
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        
       return  ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.addUser(user));
    }

    @GetMapping("/api/users/{userId}")
    public UserResponse getAllUsers(@PathVariable Long userId)
    {
        return userService.getUserbyId(userId);
   }

   @PutMapping("/api/users/{userId}")
   public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody UserRequest user){
        return ResponseEntity.ok(userService.updateUser(userId,user));
   }


}
