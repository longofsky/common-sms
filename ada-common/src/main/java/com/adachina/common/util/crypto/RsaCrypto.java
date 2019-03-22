package com.adachina.common.util.crypto;

import com.adachina.common.util.Crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:23
 */
public class RsaCrypto {
    private Charset plainTextEncoding = Charset.forName("UTF-8");
    private int keySizeBits = 1024;

    private Key publicKey;
    private Key privateKey;
    private BigInteger modulus;
    private BigInteger exponent;
    Cipher cipher;
    KeyFactory fact;

    public RsaCrypto()
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            UnsupportedEncodingException, InvalidKeyException {
        cipher = Cipher.getInstance("RSA");
        fact = KeyFactory.getInstance("RSA");
        setNewKey(keySizeBits);
    }

    public boolean setNewKey(int keySize)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (keySize <= 0) {
            return false;
        }
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keySize);
        KeyPair kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();

        RSAPublicKeySpec pub = fact.getKeySpec(publicKey, RSAPublicKeySpec.class);
        modulus = pub.getModulus();
        exponent = pub.getPublicExponent();

        return true;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public String encrypt(String plaintext)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (plaintext.length() == 0) {
            return null;
        }
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        return Crypto.Utils.byteArrayToBase64String(encrypted);
    }

    public String decrypt(String ciphertext)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (ciphertext.length() == 0) {
            return null;
        }
        byte[] dec = Crypto.Utils.base64StringToByteArray(ciphertext);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(dec);
        return new String(decrypted, plainTextEncoding);
    }
}
