package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.model.Follow;
import com.example.server.model.User;
import com.example.server.service.AuthUserService;
import com.example.server.service.FollowService;
import com.example.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class FollowController {
    private final FollowService followService;
    private final AuthUserService authService;
    private final UserService service;

    public FollowController(FollowService followService, AuthUserService authService, UserService service) {
        this.followService = followService;
        this.authService = authService;
        this.service = service;
    }

    @PostMapping (value = "/follow-to-user/{name}", consumes = {"application/json"})
    public ResponseEntity<?> addFollowToUser(@CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip, @PathVariable String name) {
        AuthUser authUser = authService.read(UUID.fromString(token));
        if(Objects.equals(authUser.getIp(), ip)){
            User user = service.read(name);
            if(followService.read(authUser.getUser(), user) ==null){
                followService.create(new Follow(authUser.getUser(), user));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping (value = "/unfollow-to-user/{name}", consumes = {"application/json"})
    public ResponseEntity<?> deleteFollowToUser(@CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip, @PathVariable String name) {
        AuthUser authUser = authService.read(UUID.fromString(token));
        if(Objects.equals(authUser.getIp(), ip)){
            User user = service.read(name);
            if(followService.read(authUser.getUser(), user) !=null){
                followService.delete(authUser.getUser(), user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
