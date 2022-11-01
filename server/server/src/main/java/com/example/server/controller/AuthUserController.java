package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.zip.DataFormatException;

@RestController
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController(AuthUserService authUserService) {
        this.service = authUserService;
    }

    @PostMapping(value = "/auth", consumes = {"application/json"})
    public ResponseEntity<UUID> authUser(@RequestBody AuthUser user) {
        try{
            UUID uuid = service.auth(user);
            return new ResponseEntity<>(uuid,HttpStatus.OK);
        } catch (DataFormatException e) {
            System.out.println("ERROR Data");
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<UUID> createRegistration(@RequestBody AuthUser user) {
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
