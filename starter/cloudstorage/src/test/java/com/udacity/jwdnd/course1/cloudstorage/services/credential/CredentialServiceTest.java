package com.udacity.jwdnd.course1.cloudstorage.services.credential;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CredentialServiceTest {

    private CredentialService sut;
    private CredentialRepository credentialRepositoryMock;
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        credentialRepositoryMock = mock(CredentialRepository.class);
        EncryptionService encryptionService = new EncryptionService();
        userServiceMock = mock(UserService.class);
        sut = new CredentialService(userServiceMock, credentialRepositoryMock, encryptionService);

    }

    @Test
    void getUnencryptedCredentialPasswordTest() {
        Credential myCredentialSample = new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );
        when(credentialRepositoryMock.getByCredentialId(100)).thenReturn(
                myCredentialSample);

        var encryptedPassword = sut.getEncryptedCredentialPassword("azerty", "HIxi7PbCRU9uIyET6sdGEg==");
        var result = sut.getUnencryptedCredentialPassword(encryptedPassword, "HIxi7PbCRU9uIyET6sdGEg==");
        assertThat(result).isEqualTo("azerty");
    }

    @Test
    void updateTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        Credential myCredentialSample = new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );

        Credential myCredentialSample2 = new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                sut.getEncryptedCredentialPassword("azerty", "HIxi7PbCRU9uIyET6sdGEg=="),
                1
        );

        when(credentialRepositoryMock.getByCredentialId(100)).thenReturn(myCredentialSample2);
        sut.update(myCredentialSample);
        verify(credentialRepositoryMock).updateCredential(myCredentialSample2);
    }

    @Test
    void deleteByCredentialIdAndUserIdTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        Credential myCredentialSample = new Credential(100,
                "https://login.oracle.com/mysso/signon.jsp",
                "Admin",
                "HIxi7PbCRU9uIyET6sdGEg==",
                "azerty",
                1
        );
        sut.deleteByCredentialIdAndUserId(100);
        verify(credentialRepositoryMock).deleteByCredentialIdAndUserId(1, 100);
    }
}