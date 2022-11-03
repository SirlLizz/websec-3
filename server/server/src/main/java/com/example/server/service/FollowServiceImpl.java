package com.example.server.service;

import com.example.server.model.Follow;
import com.example.server.model.Friend;
import com.example.server.model.User;
import com.example.server.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public void create(Follow follow){
        followRepository.save(follow);
    }

    @Override
    public List<Follow> readAll() {
        return followRepository.findAll();
    }

    @Override
    public Follow read(User fromUser, User toUser) {
        return followRepository.findFollowByTwoId(fromUser, toUser);
    }

    @Override
    public boolean delete(User fromUser, User toUser) {
        int id = followRepository.findFollowByTwoId(fromUser, toUser).getId();
        if (followRepository.existsById(id)) {
            followRepository.deleteById(id);
            return true;
        }
        return false;
    }
}