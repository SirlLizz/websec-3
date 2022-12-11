package com.example.server.controller;

import com.example.server.model.AuthUser;
import com.example.server.service.AuthUserService;
import com.example.server.source.Decrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.DataFormatException;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthUserController {

    private final AuthUserService service;
    private final Decrypt decrypt = new Decrypt();

    public AuthUserController(AuthUserService authUserService) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.service = authUserService;
    }

    @PostMapping(value = "/auth", consumes = {"application/json"})
    public ResponseEntity<?> authUser(@RequestBody AuthUser user, HttpServletRequest request) {
        try{
            user.setBrowser(request.getHeader("user-agent"));
            user.setIp(request.getRemoteAddr());
            Cookie cookie = new Cookie("token", decrypt.encrypt(service.auth(user).toString()+user.getUser().getName()+user.getUser().getEmail()));
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
    public ResponseEntity<?> createRegistration(@RequestBody AuthUser user, HttpServletRequest request) {
        try{
            user.setBrowser(request.getHeader("user-agent"));
            user.setIp(request.getRemoteAddr());
            Cookie cookie = new Cookie("token", decrypt.encrypt(service.create(user).toString()+user.getUser().getName()+user.getUser().getEmail()));
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
        String tokenDecrypt = decrypt.decrypt(token).substring(0,36);
        if(service.delete(UUID.fromString(tokenDecrypt))){
            Cookie cookie = new Cookie("token",null);
            return new ResponseEntity<>(cookie, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
