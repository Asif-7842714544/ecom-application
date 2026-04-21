package com.ecom.ecom_application.service;

import com.ecom.ecom_application.domain.User;
import com.ecom.ecom_application.reporsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User getUserbyId(Long userId) {
       return userRepo.findById(userId).orElseThrow(()-> new RuntimeException("user not found with userId " + userId));
    }
}
