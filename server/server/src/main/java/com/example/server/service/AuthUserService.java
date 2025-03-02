package com.example.server.service;

import com.example.server.model.AuthUser;

import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;

public interface AuthUserService {
    UUID create(AuthUser user) throws Exception;
    UUID auth(AuthUser user) throws Exception;

    List<AuthUser> readAll();

    AuthUser read(UUID id);

    boolean update(AuthUser user, UUID id);

    boolean delete(UUID id);
}
