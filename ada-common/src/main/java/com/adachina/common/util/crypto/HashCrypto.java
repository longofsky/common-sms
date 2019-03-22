package com.adachina.common.util.crypto;

import java.nio.charset.Charset;
import java.security.MessageDigest;


/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:25
 */
public class HashCrypto {
    private Charset plainTextEncoding = Charset.forName("UTF-8");

    public String md2(String message) {
        return hash("MD2", message);
    }

    public String md5(String message) {
        return hash("MD5", message);
    }

    public String sha1(String message) {
        return hash("SHA-1", message);
    }

    public String sha256(String message) {
        return hash("SHA-256", message);
    }

    public String sha384(String message) {
        return hash("SHA-384", message);
    }

    public String sha512(String message) {
        return hash("SHA-512", message);
    }

    private String hash(String algorithm, String message) {
        try {
            MessageDigest crypt = MessageDigest.getInstance(algorithm);
            crypt.reset();
            byte[] array = crypt.digest(message.getBytes(plainTextEncoding));
            return arrayToString(array);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String arrayToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
