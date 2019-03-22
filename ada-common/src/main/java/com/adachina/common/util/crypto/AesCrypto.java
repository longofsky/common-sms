package com.adachina.common.util.crypto;

import com.adachina.common.util.Crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:25
 */
public class AesCrypto {
    /**
     * symmetric algorithm for data encryption
     */
    private final String ALGORITHM = "AES";
    private final String MODE = "CBC";
    /**
     * Padding for symmetric algorithm
     */
    private final String PADDING = "PKCS5Padding";

    private final String CIPHER_TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;
    /**
     * character encoding
     */
    private Charset plainTextEncoding = Charset.forName("UTF-8");

    /**
     * private final String CRYPTO_PROVIDER = "SunMSCAPI";
     * provider for the crypto
     * symmetric key size (128, 192, 256)
     * if using 256 you must have the Java Cryptography Extension (JCE)
     * Unlimited Strength Jurisdiction Policy Files installed
     */
    private int keySizeBits = 128;
    private int keySizeBytes = keySizeBits / 8;

    private Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
    private byte[] ivBytes = new byte[keySizeBytes];
    private SecretKey key;

    public AesCrypto()
            throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException,
            InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        kgen.init(keySizeBits);
        key = kgen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
    }

    public String getIVAsHex() {
        return Crypto.Utils.byteArrayToHexString(ivBytes);
    }

    public String getKeyAsHex() {
        return Crypto.Utils.byteArrayToHexString(key.getEncoded());
    }

    public void setStringToKey(String keyText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        setKey(keyText.getBytes());
    }

    public void setHexToKey(String hexKey) {
        setKey(Crypto.Utils.hexStringToByteArray(hexKey));
    }

    public void setKey(byte[] bArray) {
        byte[] bText = new byte[keySizeBytes];
        int end = Math.min(keySizeBytes, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        key = new SecretKeySpec(bText, ALGORITHM);
    }

    public void setStringToIV(String ivText) {
        setIV(ivText.getBytes());
    }

    public void setHexToIV(String hexIV) {
        setIV(Crypto.Utils.hexStringToByteArray(hexIV));
    }

    public void setIV(byte[] bArray) {
        byte[] bText = new byte[keySizeBytes];
        int end = Math.min(keySizeBytes, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        ivBytes = bText;
    }

    public byte[] generateIV() {
        byte[] iv = Crypto.Utils.getRandomBytes(keySizeBytes);
        return iv;
    }

    public String encrypt(String plaintext, String passphrase)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (plaintext.length() == 0) {
            return null;
        }

        setStringToKey(passphrase);

        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(plainTextEncoding));
        return Crypto.Utils.byteArrayToBase64String(encrypted);
    }

    public String decrypt(String ciphertext, String passphrase)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (ciphertext.length() == 0) {
            return null;
        }

        setStringToKey(passphrase);

        byte[] dec = Crypto.Utils.base64StringToByteArray(ciphertext);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] decrypted = cipher.doFinal(dec);
        return new String(decrypted, plainTextEncoding);
    }
}
