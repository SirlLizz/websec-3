package com.example.server.service;

import com.example.server.model.AuthUser;
import com.example.server.model.User;
import com.example.server.repository.AuthUserRepository;
import com.example.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository, UserRepository userRepository) {
        this.authUserRepository = authUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID create(AuthUser authUser) throws DataFormatException {
        if(authUserRepository.findUserByNameOrEmail(authUser.getUser().getName(), authUser.getUser().getEmail()) == null){
            authUser.getUser().setId(UUID.randomUUID());
            userRepository.save(authUser.getUser());
            authUserRepository.save(authUser);
            return authUser.getId();
        }else{
            throw new DataFormatException();
        }
    }

    @Override
    public UUID auth(AuthUser authUser) throws DataFormatException {
        User userFromDB = authUserRepository.findUserByNameOrEmail(authUser.getUser().getName(), authUser.getUser().getEmail());
        if(userFromDB != null && Objects.equals(userFromDB.getPassword(), authUser.getUser().getPassword())){
            authUser.setUser(userFromDB);
            authUserRepository.save(authUser);
            return authUser.getId();
        }else{
            throw new DataFormatException();
        }
    }

    @Override
    public List<AuthUser> readAll() {
        return authUserRepository.findAll();
    }

    @Override
    public AuthUser read(UUID id) {
        return authUserRepository.getReferenceById(id);
    }

    @Override
    public boolean update(AuthUser user, UUID id) {
        if (authUserRepository.existsById(id)) {
            user.setId(id);
            authUserRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(UUID id) {
        if (authUserRepository.existsById(id)) {
            authUserRepository.deleteById(id);
            return true;
        }
        return false;
    }
}