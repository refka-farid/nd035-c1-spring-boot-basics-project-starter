package com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EncryptionServiceTest {

    EncryptionService sut;
    String key = "HIxi7PbCRU9uIyET6sdGEg==";

    @BeforeEach
    void setUp() {
        sut = new EncryptionService();
    }

    @Test
    void encryptValueTest() {
        String result = sut.encryptValue("azerty",key);
        assertThat(result).isEqualTo("QVZBHys7I/JBawmZF/GxVQ==");
    }

    @Test
    void decryptValueTest() {
        String result = sut.decryptValue("QVZBHys7I/JBawmZF/GxVQ==",key);
        assertThat(result).isEqualTo("azerty");
    }
}
