package com.example.server.repository;

import com.example.server.model.AuthUser;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    @Query(value = "SELECT * FROM Auth_User join public.User on Auth_User.User = public.User.id WHERE public.User.name = ?1 OR public.User.email = ?2", nativeQuery = true)
    AuthUser findAuthUserByNameOrEmail(String name, String email);

    @Query("SELECT t FROM User t WHERE t.name = ?1 OR t.email = ?2")
    User findUserByNameOrEmail(String name, String email);
}
