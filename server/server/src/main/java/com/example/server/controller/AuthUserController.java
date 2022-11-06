package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.UUID;
import java.util.zip.DataFormatException;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController(AuthUserService authUserService) {
        this.service = authUserService;
    }

    @PostMapping(value = "/auth", consumes = {"application/json"})
    public ResponseEntity<?> authUser(@RequestBody AuthUser user) {
        try{
            Cookie cookie = new Cookie("token", service.auth(user).toString());
            return new ResponseEntity<>(cookie, HttpStatus.OK);
        } catch (DataFormatException e) {
            System.out.println("ERROR Data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<?> createRegistration(@RequestBody AuthUser user) {
        try{
            Cookie cookie = new Cookie("token", service.create(user).toString());
            return new ResponseEntity<>(cookie, HttpStatus.OK);
        } catch (DataFormatException e) {
            System.out.println("ERROR Data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<?> signoutUser(@CookieValue(value = "token") String token) {
        if(service.delete(UUID.fromString(token))){
            Cookie cookie = new Cookie("token",null);
            return new ResponseEntity<>(cookie, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
