package com.udacity.jwdnd.course1.cloudstorage.services.login;

import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.HashService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashServiceTest {

    HashService sut;

    @BeforeEach
    void setUp() {
        sut = new HashService();
    }

    @Test
    void getHashedValue() {
        String salt = "HIxi7PbCRU9uIyET6sdGEg==";
        String result = sut.getHashedValue("azerty", salt);
        assertThat(result).isEqualTo("8H7jlDi3a2iPiu9ZI1+krA==");
    }
}