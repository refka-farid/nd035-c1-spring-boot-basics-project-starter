package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    // TODO: 30/05/2021 to be tested
    private final UserMapper userMapper;
    private final HashService hashService;

    // TODO: 30/05/2021 should use repository instead of mapper
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUserByUserName(username) == null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getHashedPassword(), encodedSalt);
        return userMapper.addUser(new User(null, user.getUserName(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public User getUser(String username) {
        return userMapper.getUserByUserName(username);
    }
}
