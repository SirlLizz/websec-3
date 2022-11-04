package com.example.server.service;

import com.example.server.model.AuthUser;
import com.example.server.model.Friend;
import com.example.server.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UUID create(User user) throws Exception;

    List<User> readAll();

    User read(UUID id);
    User read(String name);

    boolean update(User user, UUID id);

    boolean delete(UUID id);
}
