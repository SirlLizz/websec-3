package com.example.server.service;

import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final String uploadPath = "C:\\Users\\Ilya\\Desktop\\Web-unic\\websec-3\\server\\server\\src\\main\\java\\com\\example\\server\\photos\\";

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public UUID create(MultipartFile photo, String lend, User user) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String curDate = LocalDateTime.now().toString();
        String fileName = ("photo_" + curDate + "_" + photo.getOriginalFilename()).replaceAll("[ :]", "-");
        photo.transferTo(new File(uploadDir + "/" + fileName));
        Post post = new Post(user, lend, fileName, 0);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<Post> readAll() {
        postRepository.findAll();
        return null;
    }

    @Override
    public Post read(UUID id) {
        return null;
    }

    @Override
    public Post read(User user) {
        return null;
    }

    @Override
    public boolean update(Post post, UUID id) {
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
