package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.service.AuthUserService;
import com.example.server.service.PostService;
import com.example.server.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class PostController {
    private final PostService service;
    private final AuthUserService authService;

    private final UserService userService;

    public PostController(PostService service, AuthUserService authService, UserService userService) {
        this.service = service;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/new-post")
    @ResponseBody
    public ResponseEntity<?> addNewPost(@RequestPart(value = "photo") MultipartFile photo,
                                        @RequestPart(value = "lend") String lend,
                                        @CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip) throws IOException {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                service.create(photo, lend, authUser.getUser());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @PostMapping(value = "/add-like")
    @ResponseBody
    public ResponseEntity<?> addLikePost(@RequestPart(value = "user") String user,
                                        @CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip) throws IOException {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                service.addLike(userService.read(user));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping(value = "/user-posts", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getUserPosts(@CookieValue(value = "token") String token, @CookieValue(value = "ip") String ip) throws IOException {
        try{
            AuthUser authUser = authService.read(UUID.fromString(token));
            if(Objects.equals(authUser.getIp(), ip)){
                return new ResponseEntity<>(service.read(authUser.getUser()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get-photo/{filename:.+}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable String filename, HttpServletRequest request)
            throws IOException {
        Resource resource = service.loadFileAsResource(filename);
        String contentType =request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

