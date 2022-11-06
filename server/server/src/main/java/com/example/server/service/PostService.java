package com.example.server.service;

import com.example.server.model.Post;
import com.example.server.model.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

public interface PostService {
    UUID create(MultipartFile photo, String lend, User user) throws IOException;

    List<Post> readAll();

    Post read(UUID id);
    List<Post> read(User user) throws MalformedURLException;

    Resource loadFileAsResource(String fileName)
            throws MalformedURLException;

    boolean delete(UUID id);

    List<Post> readFollow(List<User> users);
}
