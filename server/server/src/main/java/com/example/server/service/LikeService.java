package com.example.server.service;

import com.example.server.model.Like;
import com.example.server.model.Post;
import com.example.server.model.User;

import java.util.List;

public interface LikeService {
    void create(Like like);

    List<Like> readAll();

    Like read(User fromUser, Post post);

    boolean delete(User fromUser, Post post);

    int getCountLike(Post post);

    boolean checkLike(User fromUser, Post post);
}
