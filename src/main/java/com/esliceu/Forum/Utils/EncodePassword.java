package com.esliceu.Forum.Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EncodePassword {

    public static String encode (String password) {
        String encoded = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            encoded = String.format("%0128x", new BigInteger(1,digest.digest()));
        } catch (Exception e) {
            System.err.println(e);
        }
        return encoded;
    }
}
