package com.example.server.repository;

import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT t FROM User t WHERE t.name = ?1 OR t.email = ?2")
    User findByNameOrEmail(String name, String email);

    @Query(value = "SELECT * FROM User join public.Follow on User.id = public.Follow.from_user WHERE public.Follow.from_user = ?1 AND public.Follow.to_user = ?2", nativeQuery = true)
    User checkFriendById(UUID id, UUID followToId);
}
