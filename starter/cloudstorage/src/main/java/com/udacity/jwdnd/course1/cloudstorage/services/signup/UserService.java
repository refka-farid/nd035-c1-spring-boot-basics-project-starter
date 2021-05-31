package com.udacity.jwdnd.course1.cloudstorage.services.signup;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.springframework.stereotype.Service;

import static com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.RandomSalt.getBase64Encoded;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HashService hashService;

    public UserService(UserRepository userRepository, HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.getOne(username) == null;
    }

    public boolean createUser(User user) {
        String encodedSalt = getBase64Encoded();
        String hashedPassword = hashService.getHashedValue(user.getHashedPassword(), encodedSalt);
        user.setHashedPassword(hashedPassword);
        user.setSalt(encodedSalt);
        return userRepository.add(user);
    }

    public User getUser(String username) {
        return userRepository.getOne(username);
    }
}
