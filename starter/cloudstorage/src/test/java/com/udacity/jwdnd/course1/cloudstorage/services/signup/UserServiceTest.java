package com.udacity.jwdnd.course1.cloudstorage.services.signup;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    private UserService sut;
    private UserRepository userRepositoryMock;
    private HashService hashServiceMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        hashServiceMock = mock(HashService.class);
        sut = new UserService(userRepositoryMock, hashServiceMock);
    }

    @Test
    void isUsernameAvailableTest() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.isUsernameAvailable(user1.getUserName());
        verify(userRepositoryMock).getOneByUserName(user1.getUserName());
    }

    @Test
    void createUserTest() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.createUser(user1);
        verify(hashServiceMock).getHashedValue(anyString(), anyString());
        verify(userRepositoryMock).add(user1);
    }

    @Test
    void getUserTest() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        sut.getUser(user1.getUserName());
        verify(userRepositoryMock).getOneByUserName(user1.getUserName());
    }
}