package com.example.server.controller;

import com.example.server.model.User;
import com.example.server.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.zip.DataFormatException;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/user")
    public String getUser(){
        return service.toString();
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<UUID> createRegistration(@RequestBody User user) {
        try {
            user.setId(UUID.randomUUID());
            UUID uuid = service.create(user);
            return new ResponseEntity<>(uuid,HttpStatus.OK);
        } catch (DataFormatException e) {
            System.out.println("ERROR Data");
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
