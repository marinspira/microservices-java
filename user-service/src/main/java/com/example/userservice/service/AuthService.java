package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository repo;

    public String register(User user) {
        if (repo.findByEmail(user.getEmail()) != null) {
            return "Existent user";
        }
        repo.save(user);
        return "User created successfully!";
    }

    public String login(User user) {
        User existent = repo.findByEmail(user.getEmail());
        if (existent != null && existent.getPassword().equals(user.getPassword())) {
            return "Login OK";
        }
        return "Invalid credentials";
    }
}
