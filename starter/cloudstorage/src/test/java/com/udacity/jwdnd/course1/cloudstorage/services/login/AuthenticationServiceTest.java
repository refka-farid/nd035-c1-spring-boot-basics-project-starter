package com.udacity.jwdnd.course1.cloudstorage.services.login;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {
    private AuthenticationService sut;

    private final HashService hashService = new HashService();
    private final UserRepository userRepositoryMock = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        sut = new AuthenticationService(userRepositoryMock, hashService);
    }

    @Test
    void authenticateSuccessTest() {
        var authenticationMock = mock(Authentication.class);
        when(authenticationMock.getName()).thenReturn("SALAH");
        when(authenticationMock.getCredentials()).thenReturn("azerty");

        when(userRepositoryMock.getOne("SALAH")).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );

        Authentication authenticateResult = sut.authenticate(authenticationMock);
        assertThat(authenticateResult).isInstanceOf(UsernamePasswordAuthenticationToken.class);
    }

    @Test
    void authenticateFailTest() {
        var authenticationMock = mock(Authentication.class);
        when(authenticationMock.getName()).thenReturn("SALAH");
        when(authenticationMock.getCredentials()).thenReturn("azertyaaaaaa");

        when(userRepositoryMock.getOne("SALAH")).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );

        Authentication authenticateResult = sut.authenticate(authenticationMock);
        assertThat(authenticateResult).isEqualTo(null);
    }
}
