package com.example.server.source;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class Decrypt {
    public Decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    private final SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    private final IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));

    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return new String(Base64.getMimeEncoder().encode(encrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String decrypt(String encrypted) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Decrypt decrypter = new Decrypt();
        String encrypted = decrypter.encrypt("the quick brown fox jumps over the lazy dog");
        System.out.println(encrypted);
        String decrypted = decrypter.decrypt(encrypted);
        System.out.println(decrypted);
    }
}