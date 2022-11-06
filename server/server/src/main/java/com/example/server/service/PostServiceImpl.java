package com.example.server.service;

import com.example.server.model.Follow;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.repository.PostRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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
        Post post = new Post(user, lend, fileName);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<Post> readAll() {
        return postRepository.findAll();
    }

    @Override
    public Post read(UUID id) {
        return postRepository.findById(id).orElse(new Post());
    }

    @Override
    public List<Post> read(User user) throws MalformedURLException {
        List<Post> posts = postRepository.findByUser(user);
        for(Post post: posts){
            post.setUser(new User(post.getUser().getName()));
        }
        return posts;
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path fileStorageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        return new UrlResource(filePath.toUri());
    }

    @Override
    public boolean update(Post post, UUID id) {
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public List<Post> readFollow(List<User> users) {
        List<Post> posts = new ArrayList<>();
        for(User user:users){
            List<Post> posts_user = postRepository.findByUser(user);
            for(Post post: posts_user){
                post.setUser(new User(post.getUser().getName()));
                posts.add(post);
            }
        }
        posts.sort(Post::compareTo);
        return posts;
    }
}
