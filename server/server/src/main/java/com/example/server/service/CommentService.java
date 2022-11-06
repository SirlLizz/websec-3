package com.example.server.service;

import com.example.server.model.Comment;
import com.example.server.model.Post;

import java.util.List;

public interface CommentService {

    void create(Comment comment);

    List<Comment> readAll(Post post);

    Integer getCountComment(Post post);

    Comment read(Integer id);

    boolean delete(Integer id);
}
