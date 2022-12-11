package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.service.AuthUserService;
import com.example.server.service.FollowService;
import com.example.server.service.PostService;
import com.example.server.source.Decrypt;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class PostController {
    private final PostService service;
    private final AuthUserService authService;
    private final FollowService followService;
    private final Decrypt decrypt = new Decrypt();

    public PostController(PostService service, AuthUserService authService, FollowService followService) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.service = service;
        this.authService = authService;
        this.followService = followService;
    }

    @PostMapping(value = "/new-post")
    @ResponseBody
    public ResponseEntity<?> addNewPost(@RequestPart(value = "photo") MultipartFile photo,
                                        @RequestPart(value = "lend") String lend,
                                        @CookieValue(value = "token") String token, HttpServletRequest request) {
        try{
            String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
            AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
            if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                    Objects.equals(request.getRemoteAddr(), authUser.getIp())){
                service.create(photo, lend, authUser.getUser());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user-posts", consumes = {"application/json"})
    public ResponseEntity<?> getUserPosts(@CookieValue(value = "token") String token, HttpServletRequest request) {
        try{
            String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
            AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
            System.out.println(authUser);
            if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                    Objects.equals(request.getRemoteAddr(), authUser.getIp())){
                return new ResponseEntity<>(service.read(authUser.getUser()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println("ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/follow-posts", consumes = {"application/json"})
    public ResponseEntity<?> getFollowPosts(@CookieValue(value = "token") String token, HttpServletRequest request) {
        try{
            String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
            AuthUser authUser = authService.read(UUID.fromString(tokenDecrypt));
            System.out.println(authUser);
            if(Objects.equals(request.getHeader("user-agent"), authUser.getBrowser()) &&
                    Objects.equals(request.getRemoteAddr(), authUser.getIp())){
                return new ResponseEntity<>(service.readFollow(followService.readAllFollow(authUser.getUser())), HttpStatus.OK);
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
        if(filename.matches("([^/:*?<>|\\\\]+(.(jpg|png|gif))$)")){
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
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

}

