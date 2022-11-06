package com.example.server.repository;

import com.example.server.model.Like;
import com.example.server.model.Post;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    @Query(value = "SELECT * FROM public.like WHERE public.like.post = ?1", nativeQuery = true)
    List<Like> findAllLikeByPost(UUID post_id);

    @Query("SELECT t FROM Like t WHERE t.user = ?1 AND t.post = ?2")
    Like findLikeByUserAndPost(User fromUser, Post post);
}