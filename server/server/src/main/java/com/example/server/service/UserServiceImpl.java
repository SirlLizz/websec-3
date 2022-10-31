package com.example.server.service;

import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID create(User user) throws Exception {
        if(userRepository.findByNameOrEmail(user.getName(), user.getEmail()) == null){
            userRepository.save(user);
            return user.getId();
        }else{
            throw new DataFormatException();
        }
    }

    @Override
    public UUID auth(User user) throws Exception {
        User userFromDB = userRepository.findByNameOrEmail(user.getName(), user.getEmail());
        if(userFromDB != null && Objects.equals(userFromDB.getPassword(), user.getPassword())){
            return userFromDB.getId();
        }else{
            throw new DataFormatException();
        }
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(UUID id) {
        return userRepository.getReferenceById(id);
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