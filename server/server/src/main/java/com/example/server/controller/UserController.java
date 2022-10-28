package com.example.server.controller;

import com.example.server.model.User;
import com.example.server.service.UserService;
import com.example.server.service.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
