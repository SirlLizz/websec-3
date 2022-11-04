package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.model.User;
import com.example.server.service.AuthUserService;
import com.example.server.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class PostController {
    private final PostService service;
    private final AuthUserService authService;

    public PostController(PostService service, AuthUserService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping(value = "/new-post")
    @ResponseBody
    public ResponseEntity<?> addNewPost(@RequestPart(value = "photo") MultipartFile photo, @RequestPart(value = "lend") String lend,  @CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip) throws IOException {
        //try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                service.create(photo, lend, authUser.getUser());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
/*        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }
}
