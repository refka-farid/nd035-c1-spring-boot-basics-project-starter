package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class UserRepositoryTest {

    @Inject
    private UserMapper mapper;
    private UserRepository sut;

    @BeforeEach
    void setUp() {
        sut = new UserRepository(mapper);
    }

    @AfterEach
    void tearDown() {
        mapper.deleteAll();
    }

    @Test
    void addOneTest() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        boolean isAdded = sut.add(user1);
        assertThat(isAdded).isTrue();
        assertThat(sut.delete1(user1)).isTrue();
    }

    @Test
    void addAllTest() {
        User user1 = User.from("aaaaa_userName", "aaaaa_password", "aaaaa_firstName", "aaaaa_lastName");
        User user2 = User.from("zzzzz_userName", "zzzzz_password", "zzzzz_firstName", "zzzzz_lastName");

        boolean result = sut.addAll(List.of(
                user1,
                user2,
                User.from("eeeer_userName", "eeeer_password", "eeeer_firstName", "eeeer_lastName"),
                User.from("rrrrr_userName", "rrrrr_password", "rrrrr_firstName", "rrrrr_lastName")
        ));
        assertThat(result).isTrue();

        boolean isDeleted = sut.delete1(user1);
        assertThat(isDeleted).isTrue();

        boolean isDeleted2 = sut.delete2(user2);
        assertThat(isDeleted2).isTrue();

        boolean allDeleted = sut.deleteAll();
        assertThat(allDeleted).isTrue();
    }

    @Test
    void updateUserTest() {
         sut.addAll(List.of(
                User.from("eeeer_userName", "eeeer_password", "eeeer_firstName", "eeeer_lastName"),
                User.from("rrrrr_userName", "rrrrr_password", "rrrrr_firstName", "rrrrr_lastName")
        ));
        User user1 = User.from("rrrrr_userName", "rrrrr_password", "MMMM_firstName", "MMMM_lastName");
        User result = sut.updateOrNull(user1);
        assertThat(result.getUserName()).isEqualTo("rrrrr_userName");
        assertThat(result.getFirstName()).isEqualTo("MMMM_firstName");
        assertThat(result.getLastName()).isEqualTo("MMMM_lastName");
    }


    @Test
    void addOrUpdateUserTest() {
        sut.addAll(List.of(
                User.from("eeeer_userName", "eeeer_password", "eeeer_firstName", "eeeer_lastName"),
                User.from("rrrrr_userName", "rrrrr_password", "rrrrr_firstName", "rrrrr_lastName")
        ));
        User user1 = User.from("rrrrr_userName", "rrrrr_password", "MMMM_firstName", "MMMM_lastName");
        User result = sut.addOrUpdate(user1);
        assertThat(result.getUserName()).isEqualTo("rrrrr_userName");
        assertThat(result.getFirstName()).isEqualTo("MMMM_firstName");
        assertThat(result.getLastName()).isEqualTo("MMMM_lastName");

        User user2 = User.from("YYYY_userName", "YYYY_password", "YYYY_firstName", "YYYY_lastName");
        User result2 = sut.addOrUpdate(user2);
        assertThat(result2.getUserName()).isEqualTo("YYYY_userName");
        assertThat(result2.getFirstName()).isEqualTo("YYYY_firstName");
        assertThat(result2.getLastName()).isEqualTo("YYYY_lastName");
    }
}