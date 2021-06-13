package com.udacity.jwdnd.course1.cloudstorage.services.signup;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        return userRepository.getOneByUserName(username) == null;
    }

    public boolean createUser(User user) {
        String encodedSalt = getBase64Encoded();
        String hashedPassword = hashService.getHashedValue(user.getHashedPassword(), encodedSalt);
        user.setHashedPassword(hashedPassword);
        user.setSalt(encodedSalt);
        return userRepository.add(user);
    }

    public User getUser(String username) {
        return userRepository.getOneByUserName(username);
    }

    public User getAuthenticatedUser() {
        return getOneByUserName(getAuthenticatedUserName());
    }

    public User getOneByUserName(String userName) {
        return userRepository.getOneByUserName(userName);
    }

    private String getAuthenticatedUserName() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
