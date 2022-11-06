package com.example.server.repository;

import com.example.server.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @Query(value = "SELECT * FROM public.comment WHERE public.comment.post = ?1", nativeQuery = true)
    List<Comment> findAllCommentByPost(UUID post_id);

    @Query("SELECT t FROM Comment t WHERE t.id = ?1")
    Comment findCommentById(Integer id);
}