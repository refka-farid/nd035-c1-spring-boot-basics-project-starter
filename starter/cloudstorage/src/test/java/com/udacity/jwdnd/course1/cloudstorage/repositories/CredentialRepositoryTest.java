package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class CredentialRepositoryTest {

    @Inject
    private CredentialMapper mapperCredential;

    @Inject
    private UserMapper mapperUser;

    private CredentialRepository sut;

    private Credential myCredential;
    private User user;

    @BeforeEach
    void setUp() {
        sut = new CredentialRepository(mapperCredential);
        user = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        mapperUser.addUser(user);
        myCredential = new Credential(null, "https://login.oracle.com/mysso/signon.jsp", "Admin", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", user.getUserId());
    }

    @AfterEach
    void tearDown() {
        mapperCredential.deleteAll();
        mapperUser.deleteAll();
    }

    @Test
    void getByUrlTest() {
        var result = sut.add(myCredential);
        assertThat(result).isTrue();

        var result2 = sut.getByUrl("https://login.oracle.com/mysso/signon.jsp");
        assertThat(result2).isEqualTo(myCredential);

    }

    @Test
    void getByCredentialIdTest() {
        sut.add(myCredential);
        var credential = sut.getByUrl(myCredential.getUrl());
        var result = sut.getByCredentialId(credential.getCredentialId());
        assertThat(result).isEqualTo(myCredential);
    }

    @Test
    void getByUrlAndUserIdTest() {
        sut.add(myCredential);
        var credential = sut.getByUrlAndUserId(myCredential.getUserId(), myCredential.getUrl());
        assertThat(credential).isEqualTo(myCredential);
    }

    @Test
    void getAllTest() {
        sut.add(myCredential);
        var result = sut.getAll(user.getUserId());
        var list = new ArrayList<Credential>();
        list.add(myCredential);
        assertThat(result).isEqualTo(list);
    }

    @Test
    void updateCredentialTest() {
        sut.add(myCredential);
        var credential = sut.getByUrl(myCredential.getUrl());
        credential.setUserName("Admin2");
        var result = sut.updateCredential(credential);
        var updatedCredential = sut.getByUrl(myCredential.getUrl());
        assertThat(result).isTrue();
        assertThat(credential.getUserName()).isEqualTo(updatedCredential.getUserName());
    }

    @Test
    void deleteByUrlTest() {
        sut.add(myCredential);
        var result = sut.deleteByUrl(myCredential.getUrl());
        assertThat(result).isPositive();
    }

    @Test
    void deleteByUrl2Test() {
        sut.add(myCredential);
        var result = sut.deleteByUrl2(myCredential.getUrl());
        assertThat(result).isTrue();
    }

    @Test
    void deleteAllTest() {
        sut.add(myCredential);
        var result = sut.deleteAll();
        assertThat(result).isTrue();
    }

    @Test
    void deleteByCredentialIdAndUserIdTest() {
        sut.add(myCredential);
        var credential = sut.getByUrl(myCredential.getUrl());
        var result = sut.deleteByCredentialIdAndUserId(user.getUserId(), credential.getCredentialId());
        assertThat(result).isTrue();
    }
}
