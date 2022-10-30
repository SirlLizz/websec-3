package com.example.server.repository;

import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT t FROM User t WHERE t.name = ?1 OR t.email = ?2")
    User findByNameOrEmail(String name, String email);
}
