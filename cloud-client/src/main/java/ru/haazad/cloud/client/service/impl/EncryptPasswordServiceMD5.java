package ru.haazad.cloud.client.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.haazad.cloud.client.service.EncryptPasswordService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPasswordServiceMD5 implements EncryptPasswordService {
    private static final Logger logger = LogManager.getLogger(EncryptPasswordServiceMD5.class);

    private static EncryptPasswordServiceMD5 encryptService;

    private EncryptPasswordServiceMD5(){}

    public static EncryptPasswordServiceMD5 getEncryptService() {
        encryptService = new EncryptPasswordServiceMD5();
        return encryptService;
    }

    @Override
    public String encryptPassword(String password) {
        MessageDigest digest;
        byte[] b = new byte[0];
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            b = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.throwing(Level.ERROR, e);
        }
        BigInteger bigInt = new BigInteger(1, b);
        StringBuilder pass = new StringBuilder(bigInt.toString(16));
        while (pass.length() < 32) {
            pass.insert(0, "0");
        }
        return pass.toString();
    }
}
