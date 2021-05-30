package com.udacity.jwdnd.course1.cloudstorage.services.signup;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService sut;
    private UserRepository userRepository;
    private HashService hashServiceMock;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        hashServiceMock = mock(HashService.class);
        sut = new UserService(userRepository, hashServiceMock);
    }

    @Test
    void isUsernameAvailable() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.isUsernameAvailable(user1.getUserName());
        verify(userRepository).get(user1.getUserName());
    }

    @Test
    void createUser() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.createUser(user1);
        verify(hashServiceMock).getHashedValue(anyString(), anyString());
        verify(userRepository).add(user1);
    }

    @Test
    void getUser() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.getUser(user1.getUserName());
        verify(userRepository).get(user1.getUserName());
    }
}