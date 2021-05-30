package com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomSalt {
    public static String getBase64Encoded() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
