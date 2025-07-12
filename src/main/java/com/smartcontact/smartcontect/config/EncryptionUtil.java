package com.smartcontact.smartcontect.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ALGO = "AES";
    private static final String SECRET_KEY = "1234567890123456"; // Must be 16 bytes

    public static String encrypt(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(data.getBytes());
        return Base64.getUrlEncoder().encodeToString(encVal);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getUrlDecoder().decode(encryptedData);
        byte[] decValue = cipher.doFinal(decoded);
        return new String(decValue);
    }
}

