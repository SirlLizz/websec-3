package com.example.server.repository;

import com.example.server.model.Post;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query(value = "SELECT * FROM public.posts WHERE public.posts.user = ?1", nativeQuery = true)
    List<Post> findByUser(User user);
}
