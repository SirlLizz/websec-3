package com.example.server.service;

import com.example.server.model.Follow;
import com.example.server.model.Friend;
import com.example.server.model.User;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    void create(Follow follow);

    List<Follow> readAll();

    Follow read(User fromUser, User toUser);

    boolean delete(User fromUser, User toUser);
}
