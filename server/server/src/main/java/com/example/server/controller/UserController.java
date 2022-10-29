package com.example.server.controller;

import com.example.server.model.User;
import com.example.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/user")
    public String getUser(){
        return service.toString();
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<UUID> createRegistration(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "email", required = false) String email,
                                   @RequestParam (value = "password", required = false) String password) {
        try {
            UUID uuid = service.create(new User(name, email, password));
            return new ResponseEntity<>(uuid,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
