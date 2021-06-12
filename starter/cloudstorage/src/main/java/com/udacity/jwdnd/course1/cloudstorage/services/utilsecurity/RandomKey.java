package com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomKey {
    public static String getBase64EncodedKey() {
        var random = new SecureRandom();
        var key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
