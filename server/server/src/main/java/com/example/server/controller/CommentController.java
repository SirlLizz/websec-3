package com.example.server.controller;

import com.example.server.model.*;
import com.example.server.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class CommentController {

    private final CommentService service;
    private final AuthUserService authService;
    private final PostService postService;

    public CommentController(CommentService service, AuthUserService authService, PostService postService) {
        this.service = service;
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping(value = "/add-comment", consumes = {"application/json"})
    public ResponseEntity<?> addCommentPost(@RequestBody Comment comment,
                                         @CookieValue(value = "token") String token,
                                         @CookieValue(value = "ip") String ip) {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                User user = authUser.getUser();
                Post post = postService.read(comment.getPost().getId());
                service.create(new Comment(user, post, comment.getText()));
                return new ResponseEntity<>(service.getCountComment(post), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-comment/{post_id}", consumes = {"application/json"})
    public ResponseEntity<?> getComment(@PathVariable("post_id") String post_id,
                                            @CookieValue(value = "token") String token,
                                            @CookieValue(value = "ip") String ip) {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                Post post = postService.read(UUID.fromString(post_id));
                return new ResponseEntity<>(service.readAll(post), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-count-comment/{post_id}", consumes = {"application/json"})
    public ResponseEntity<?> getCommentPost(@PathVariable("post_id") String post_id,
                                         @CookieValue(value = "token") String token,
                                         @CookieValue(value = "ip") String ip) {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                Post post = postService.read(UUID.fromString(post_id));
                return new ResponseEntity<>(service.getCountComment(post), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
