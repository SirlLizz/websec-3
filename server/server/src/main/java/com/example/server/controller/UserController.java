package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.model.Friend;
import com.example.server.model.User;
import com.example.server.service.AuthUserService;
import com.example.server.service.FollowService;
import com.example.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService service;
    private final AuthUserService authService;
    private final FollowService followService;

    public UserController(UserService userService, AuthUserService authService, FollowService followService) {
        this.service = userService;
        this.authService = authService;
        this.followService = followService;
    }

    @GetMapping(value = "/get-all-user", consumes = {"application/json"})
    public ResponseEntity<?> getAllUser(@CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip) {
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                List<User> users = service.readAll();
                List<Friend> friend = new ArrayList<>();
                for (User user:users) {
                    if(!Objects.equals(user, authUser.getUser())){
                        if(followService.read(authUser.getUser(), user) != null){
                            friend.add(new Friend(user.getName(), true));
                        }else{
                            friend.add(new Friend(user.getName(), false));
                        }
                    }
                }
                return new ResponseEntity<>(friend, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
