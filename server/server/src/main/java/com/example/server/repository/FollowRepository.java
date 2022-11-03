package com.example.server.repository;

import com.example.server.model.Follow;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Query("SELECT t FROM Follow t WHERE t.fromUser = ?1 AND t.toUser = ?2")
    Follow findFollowByTwoId(User fromUser, User toUser);
}
