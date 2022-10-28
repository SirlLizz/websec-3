package com.example.server.service;

import com.example.server.model.User;

import java.util.List;

public interface UserService {
    void create(User user);

    List<User> readAll();

    User read(int id);

    boolean update(User user, int id);

    boolean delete(int id);
}
