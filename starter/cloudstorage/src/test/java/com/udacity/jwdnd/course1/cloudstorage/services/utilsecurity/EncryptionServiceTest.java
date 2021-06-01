package com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EncryptionServiceTest {

    private EncryptionService sut;

    @BeforeEach
    void setUp() {
        sut = new EncryptionService();
    }

    @Test
    void encryptThenDecryptValueTest() {
        final String key = RandomSalt.getBase64Encoded();

        String encryptedValue = sut.encryptValue("azerty", key);
        String result = sut.decryptValue(encryptedValue, key);
        assertThat(result).isEqualTo("azerty");
    }

}
