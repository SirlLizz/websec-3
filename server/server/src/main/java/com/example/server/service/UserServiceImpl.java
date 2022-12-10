package com.example.server.service;

import com.example.server.model.User;
import com.example.server.repository.FollowRepository;
import com.example.server.repository.UserRepository;
import com.example.server.source.Decrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Decrypt decrypt = new Decrypt();

    public UserServiceImpl(UserRepository userRepository, FollowRepository followRepository) throws Exception {
        this.userRepository = userRepository;
    }

    @Override
    public UUID create(User user) throws Exception {
        if(userRepository.findByNameOrEmail(user.getName(), user.getEmail()) == null){
            user.setPassword(decrypt.encrypt(user.getPassword()));
            userRepository.save(user);
            return user.getId();
        }else{
            throw new DataFormatException();
        }
    }

    @Override
    public List<User> readAll() throws Exception {
        List<User> users = userRepository.findAll();
        for(User user: users){
            user.setPassword(decrypt.decrypt(user.getPassword()));
        }
        return users;
    }

    @Override
    public User read(UUID id) throws Exception {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User read(String name) throws Exception {
        return userRepository.findByName(name);
    }

    @Override
    public boolean update(User user, UUID id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}