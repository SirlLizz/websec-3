package com.example.server.service;

import com.example.server.model.Post;
import com.example.server.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PostService {
    UUID create(MultipartFile photo, String lend, User user) throws IOException;

    List<Post> readAll();

    Post read(UUID id);
    Post read(User user);

    boolean update(Post post, UUID id);

    boolean delete(UUID id);
}
