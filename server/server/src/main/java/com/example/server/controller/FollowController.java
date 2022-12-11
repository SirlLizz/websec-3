package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.model.Follow;
import com.example.server.model.User;
import com.example.server.service.AuthUserService;
import com.example.server.service.FollowService;
import com.example.server.service.UserService;
import com.example.server.source.Decrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class FollowController {
    private final FollowService followService;
    private final AuthUserService authService;
    private final UserService service;
    private final Decrypt decrypt = new Decrypt();

    public FollowController(FollowService followService, AuthUserService authService, UserService service) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.followService = followService;
        this.authService = authService;
        this.service = service;
    }

    @PostMapping (value = "/follow-to-user/{name}", consumes = {"application/json"})
    public ResponseEntity<?> addFollowToUser(@CookieValue(value = "token") String token, HttpServletRequest request, @PathVariable String name) throws Exception {
        String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
        AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
        if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                Objects.equals(request.getRemoteAddr(), authUser.getIp())){
            User user = service.read(name);
            if(followService.read(authUser.getUser(), user) ==null){
                followService.create(new Follow(authUser.getUser(), user));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping (value = "/unfollow-to-user/{name}", consumes = {"application/json"})
    public ResponseEntity<?> deleteFollowToUser(@CookieValue(value = "token") String token, HttpServletRequest request, @PathVariable String name) throws Exception {
        String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
        AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
        System.out.println(authUser);
        if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                Objects.equals(request.getRemoteAddr(), authUser.getIp())){
            User user = service.read(name);
            if(followService.read(authUser.getUser(), user) !=null){
                followService.delete(authUser.getUser(), user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
