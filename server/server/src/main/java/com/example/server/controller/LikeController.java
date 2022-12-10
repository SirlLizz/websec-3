package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.model.Like;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.service.AuthUserService;
import com.example.server.service.LikeService;
import com.example.server.service.PostService;
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
public class LikeController {
    private final LikeService service;
    private final AuthUserService authService;
    private final PostService postService;
    private final Decrypt decrypt = new Decrypt();

    public LikeController(LikeService service, AuthUserService authService, PostService postService) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.service = service;
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping(value = "/add-like/{post_id}/{user}", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> addLikePost(@PathVariable("user") String user_name,
                                         @PathVariable("post_id") String post_id,
                                         @CookieValue(value = "token") String token,
                                         HttpServletRequest request) {
        try{
            String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
            AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
            System.out.println(authUser);
            if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                    Objects.equals(request.getRemoteAddr(), authUser.getIp())){
                User user = authUser.getUser();
                Post post = postService.read(UUID.fromString(post_id));
                if(service.checkLike(user, post)){
                    service.delete(user, post);
                }else{
                    service.create(new Like(user, post));
                }
                return new ResponseEntity<>(service.getCountLike(post), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-like/{post_id}", consumes = {"application/json"})
    public ResponseEntity<?> getLikePost(@PathVariable("post_id") String post_id,
                                         @CookieValue(value = "token") String token,
                                         HttpServletRequest request) {
        try{
            String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
            AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
            System.out.println(authUser);
            if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                    Objects.equals(request.getRemoteAddr(), authUser.getIp())){
                Post post = postService.read(UUID.fromString(post_id));
                return new ResponseEntity<>(service.getCountLike(post), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
