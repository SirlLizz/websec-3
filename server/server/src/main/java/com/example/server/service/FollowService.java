package com.example.server.service;

import com.example.server.model.Follow;
import com.example.server.model.User;

import java.util.List;

public interface FollowService {
    void create(Follow follow);

    List<Follow> readAll();

    List<User> readAllFollow(User user);

    Follow read(User fromUser, User toUser);

    boolean delete(User fromUser, User toUser);
}
