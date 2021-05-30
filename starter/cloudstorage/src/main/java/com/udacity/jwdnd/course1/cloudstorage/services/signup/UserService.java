package com.udacity.jwdnd.course1.cloudstorage.services.signup;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.springframework.stereotype.Service;

import static com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.RandomSalt.getBase64Encoded;

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
        String encodedSalt = getBase64Encoded();
        String hashedPassword = hashService.getHashedValue(user.getHashedPassword(), encodedSalt);
        return userMapper.addUser(new User(null, user.getUserName(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }



    public User getUser(String username) {
        return userMapper.getUserByUserName(username);
    }
}
