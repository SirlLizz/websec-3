package com.example.server.service;

import com.example.server.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UUID create(User user) throws Exception;

    List<User> readAll() throws Exception;

    User read(UUID id) throws Exception;
    User read(String name) throws Exception;

    boolean update(User user, UUID id);

    boolean delete(UUID id);
}
