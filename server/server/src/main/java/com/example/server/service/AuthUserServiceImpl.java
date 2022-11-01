package com.example.server.service;

import com.example.server.model.AuthUser;
import com.example.server.model.User;
import com.example.server.repository.AuthUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UUID create(AuthUser authUser) throws Exception {
        if(authUserRepository.findAuthUserByNameOrEmail(authUser.getUser().getName(), authUser.getUser().getEmail()) == null){
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
            return userFromDB.getId();
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