package com.adachina.common.util;

import com.adachina.common.util.crypto.HashCrypto;
import com.adachina.common.util.crypto.Pbkdf2Crypto;
import com.adachina.common.util.crypto.RsaCrypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 本类提供了：<br/>
 * 1、AES-CBC-PKCS5Padding <br/>
 * 2、PBKDF2 <br/>
 * 3、RSA <br/>
 * 4、Hash <br/>
 * 5、Base64Encode <br/>
 * 6、Utils 一些加密用到的工具 <br/>
 *
 * @author 蕉鹿
 * @2016.07.21
 */
public class Crypto {
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * AES/CBC/PKCS5Padding
     * @deprecated since 3.0.1,
     * replaced by <code>com.mgzf.sdk.util.crypto.AesCrypto</code>.
     * @deprecated
     */
    @Deprecated
    public class AES {
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
        private Charset plainTextEncoding = Charset.forName(CHARSET_UTF8);

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

        public AES()
                throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException,
                InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            kgen.init(keySizeBits);
            key = kgen.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        }

        public String getIVAsHex() {
            return Utils.byteArrayToHexString(ivBytes);
        }

        public String getKeyAsHex() {
            return Utils.byteArrayToHexString(key.getEncoded());
        }

        public void setStringToKey(String keyText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            setKey(keyText.getBytes());
        }

        public void setHexToKey(String hexKey) {
            setKey(Utils.hexStringToByteArray(hexKey));
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
            setIV(Utils.hexStringToByteArray(hexIV));
        }

        public void setIV(byte[] bArray) {
            byte[] bText = new byte[keySizeBytes];
            int end = Math.min(keySizeBytes, bArray.length);
            System.arraycopy(bArray, 0, bText, 0, end);
            ivBytes = bText;
        }

        public byte[] generateIV() {
            byte[] iv = Utils.getRandomBytes(keySizeBytes);
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
            return Utils.byteArrayToBase64String(encrypted);
        }

        public String decrypt(String ciphertext, String passphrase)
                throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException,
                InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
            if (ciphertext.length() == 0) {
                return null;
            }

            setStringToKey(passphrase);

            byte[] dec = Utils.base64StringToByteArray(ciphertext);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
            byte[] decrypted = cipher.doFinal(dec);
            return new String(decrypted, plainTextEncoding);
        }
    }


    /**
     * HASH
     * @deprecated since 3.0.1,
     * replaced by <code>com.mgzf.sdk.util.crypto.HashCrypto</code>.
     * @deprecated
     */
    @Deprecated
    public class HASH extends HashCrypto {
    }

    /**
     * RSA
     * @deprecated since 3.0.1,
     * replaced by <code>com.mgzf.sdk.util.crypto.RsaCrypto</code>.
     * @deprecated
     */
    @Deprecated
    public class RSA extends RsaCrypto {
        public RSA()
                throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
                UnsupportedEncodingException, InvalidKeyException {
            super();
        }
    }


    private static final int DK_LEN_16= 16;
    private static final int DK_LEN_24= 24;
    private static final int DK_LEN_32= 32;
    /**
     * Utils
     */
    public static class Utils {
        private Utils() {
            throw new IllegalStateException("Utils Utility class");
        }
        public static String pbkdf2(String password, String salt, int iterationCount, int dkLen)
                throws InvalidKeyException, NoSuchAlgorithmException {
            if (dkLen != DK_LEN_16 && dkLen != DK_LEN_24 && dkLen != DK_LEN_32) {
                dkLen = 16;
            }
            if (iterationCount < 0) {
                iterationCount = 0;
            }

            byte[] passwordBytes = password.getBytes();
            byte[] saltBytes = salt.getBytes();
            byte[] key = Pbkdf2Crypto.deriveKey(passwordBytes, saltBytes, iterationCount, dkLen);
            return new String(key);
        }

        public static byte[] getRandomBytes(int len) {
            if (len < 0) {
                len = 8;
            }
            Random ranGen = new SecureRandom();
            byte[] aesKey = new byte[len];
            ranGen.nextBytes(aesKey);
            return aesKey;
        }

        public static String byteArrayToHexString(byte[] raw) {
            StringBuilder sb = new StringBuilder(2 + raw.length * 2);
            sb.append("0x");
            for (byte r : raw) {
                sb.append(String.format("%02X", r & 0xFF));
            }
            return sb.toString();
        }

        public static byte[] hexStringToByteArray(String hex) {
            Pattern replace = compile("^0x");
            String s = replace.matcher(hex).replaceAll("");

            byte[] b = new byte[s.length() / 2];
            for (int i = 0; i < b.length; i++) {
                int index = i * 2;
                int v = Integer.parseInt(s.substring(index, index + 2), 16);
                b[i] = (byte) v;
            }
            return b;
        }

        public static String byteArrayToBase64String(byte[] raw) {
            return new String(Base64Coder.encode(raw));
        }

        public static byte[] base64StringToByteArray(String str) {
            return Base64Coder.decode(str);
        }

        public static String base64Encode(String str) {
            return Base64Coder.encodeString(str);
        }

        public static String base64Decode(String str) {
            return Base64Coder.decodeString(str);
        }
    }

    /**
     * Base64Coder
     */
    private static class Base64Coder {
        /**
         * The line separator string of the operating system.
         */
        private static final String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");

        /**
         * Mapping table from 6-bit nibbles to Base64 characters.
         */
        private static final char[] MAP_1 = new char[64];

        private static final char CHAR_A_U = 'A';
        private static final char CHAR_Z_U = 'Z';
        private static final char CHAR_A_L = 'a';
        private static final char CHAR_Z_L = 'z';
        private static final char CHAR_0 = '0';
        private static final char CHAR_9 = '9';
        static {
            int i = 0;
            for (char c = CHAR_A_U; c <= CHAR_Z_U; c++) {
                MAP_1[i++] = c;
            }
            for (char c = CHAR_A_L; c <= CHAR_Z_L; c++) {
                MAP_1[i++] = c;
            }
            for (char c = CHAR_0; c <= CHAR_9; c++) {
                MAP_1[i++] = c;
            }
            MAP_1[i++] = '+';
            MAP_1[i++] = '/';
        }

        /**
         * Mapping table from Base64 characters to 6-bit nibbles.
         */
        private static final byte[] MAP_2 = new byte[128];
        private static final int LEN = 64;
        static {
            for (int i = 0; i < MAP_2.length; i++) {
                MAP_2[i] = -1;
            }
            for (int i = 0; i < LEN; i++) {
                MAP_2[MAP_1[i]] = (byte) i;
            }
        }

        /**
         * Encodes a string into Base64 format.
         * No blanks or line breaks are inserted.
         *
         * @param s A String to be encoded.
         * @return A String containing the Base64 encoded data.
         */
        public static String encodeString(String s) {
            return new String(encode(s.getBytes()));
        }

        /**
         * Encodes a byte array into Base 64 format and breaks the output into lines of 76 characters.
         * This method is compatible with <code>sun.misc.BASE64Encoder.encodeBuffer(byte[])</code>.
         *
         * @param in An array containing the data bytes to be encoded.
         * @return A String containing the Base64 encoded data, broken into lines.
         */
        public static String encodeLines(byte[] in) {
            return encodeLines(in, 0, in.length, 76, SYSTEM_LINE_SEPARATOR);
        }

        /**
         * Encodes a byte array into Base 64 format and breaks the output into lines.
         *
         * @param in            An array containing the data bytes to be encoded.
         * @param iOff          Offset of the first byte in <code>in</code> to be processed.
         * @param iLen          Number of bytes to be processed in <code>in</code>, starting at <code>iOff</code>.
         * @param lineLen       Line length for the output data. Should be a multiple of 4.
         * @param lineSeparator The line separator to be used to separate the output lines.
         * @return A String containing the Base64 encoded data, broken into lines.
         */
        public static String encodeLines(byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
            int blockLen = (lineLen * 3) / 4;
            if (blockLen <= 0) {
                throw new IllegalArgumentException();
            }
            int lines = (iLen + blockLen - 1) / blockLen;
            int bufLen = ((iLen + 2) / 3) * 4 + lines * lineSeparator.length();
            StringBuilder buf = new StringBuilder(bufLen);
            int ip = 0;
            while (ip < iLen) {
                int l = Math.min(iLen - ip, blockLen);
                buf.append(encode(in, iOff + ip, l));
                buf.append(lineSeparator);
                ip += l;
            }
            return buf.toString();
        }

        /**
         * Encodes a byte array into Base64 format.
         * No blanks or line breaks are inserted in the output.
         *
         * @param in An array containing the data bytes to be encoded.
         * @return A character array containing the Base64 encoded data.
         */
        public static char[] encode(byte[] in) {
            return encode(in, 0, in.length);
        }

        /**
         * Encodes a byte array into Base64 format.
         * No blanks or line breaks are inserted in the output.
         *
         * @param in   An array containing the data bytes to be encoded.
         * @param iLen Number of bytes to process in <code>in</code>.
         * @return A character array containing the Base64 encoded data.
         */
        public static char[] encode(byte[] in, int iLen) {
            return encode(in, 0, iLen);
        }

        /**
         * Encodes a byte array into Base64 format.
         * No blanks or line breaks are inserted in the output.
         *
         * @param in   An array containing the data bytes to be encoded.
         * @param iOff Offset of the first byte in <code>in</code> to be processed.
         * @param iLen Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
         * @return A character array containing the Base64 encoded data.
         */
        public static char[] encode(byte[] in, int iOff, int iLen) {
            // output length without padding
            int oDataLen = (iLen * 4 + 2) / 3;
            // output length including padding
            int oLen = ((iLen + 2) / 3) * 4;
            char[] out = new char[oLen];
            int ip = iOff;
            int iEnd = iOff + iLen;
            int op = 0;
            while (ip < iEnd) {
                int i0 = in[ip++] & 0xff;
                int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
                int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
                int o0 = i0 >>> 2;
                int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
                int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
                int o3 = i2 & 0x3F;
                out[op++] = MAP_1[o0];
                out[op++] = MAP_1[o1];
                out[op] = op < oDataLen ? MAP_1[o2] : '=';
                op++;
                out[op] = op < oDataLen ? MAP_1[o3] : '=';
                op++;
            }
            return out;
        }

        /**
         * Decodes a string from Base64 format.
         * No blanks or line breaks are allowed within the Base64 encoded input data.
         *
         * @param s A Base64 String to be decoded.
         * @return A String containing the decoded data.
         * @exception IllegalArgumentException If the input is not valid Base64 encoded data.
         */
        public static String decodeString(String s) {
            return new String(decode(s));
        }

        /**
         * Decodes a byte array from Base64 format and ignores line separators, tabs and blanks.
         * CR, LF, Tab and Space characters are ignored in the input data.
         * This method is compatible with <code>sun.misc.BASE64Decoder.decodeBuffer(String)</code>.
         *
         * @param s A Base64 String to be decoded.
         * @return An array containing the decoded data bytes.
         * @exception IllegalArgumentException If the input is not valid Base64 encoded data.
         */
        public static byte[] decodeLines(String s) {
            char[] buf = new char[s.length()];
            int p = 0;
            for (int ip = 0; ip < s.length(); ip++) {
                char c = s.charAt(ip);
                if (c != ' ' && c != '\r' && c != '\n' && c != '\t') {
                    buf[p++] = c;
                }
            }
            return decode(buf, 0, p);
        }

        /**
         * Decodes a byte array from Base64 format.
         * No blanks or line breaks are allowed within the Base64 encoded input data.
         *
         * @param s A Base64 String to be decoded.
         * @return An array containing the decoded data bytes.
         * @exception IllegalArgumentException If the input is not valid Base64 encoded data.
         */
        public static byte[] decode(String s) {
            return decode(s.toCharArray());
        }

        /**
         * Decodes a byte array from Base64 format.
         * No blanks or line breaks are allowed within the Base64 encoded input data.
         *
         * @param in A character array containing the Base64 encoded data.
         * @return An array containing the decoded data bytes.
         * @exception IllegalArgumentException If the input is not valid Base64 encoded data.
         */
        public static byte[] decode(char[] in) {
            return decode(in, 0, in.length);
        }

        private static final int MOD = 4;
        private static final char CHAR_EQUAL = '=';
        /**
         * Decodes a byte array from Base64 format.
         * No blanks or line breaks are allowed within the Base64 encoded input data.
         *
         * @param in   A character array containing the Base64 encoded data.
         * @param iOff Offset of the first character in <code>in</code> to be processed.
         * @param iLen Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
         * @return An array containing the decoded data bytes.
         * @exception IllegalArgumentException If the input is not valid Base64 encoded data.
         */
        public static byte[] decode(char[] in, int iOff, int iLen) {
            iLen = getLenValue(in, iOff, iLen);
            int oLen = (iLen * 3) / 4;
            byte[] out = new byte[oLen];
            int ip = iOff;
            int iEnd = iOff + iLen;
            int op = 0;
            while (ip < iEnd) {
                int i0 = in[ip++];
                int i1 = in[ip++];
                int i2 = ip < iEnd ? in[ip++] : 'A';
                int i3 = ip < iEnd ? in[ip++] : 'A';
                if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int b0 = MAP_2[i0];
                int b1 = MAP_2[i1];
                int b2 = MAP_2[i2];
                int b3 = MAP_2[i3];
                if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int o0 = (b0 << 2) | (b1 >>> 4);
                int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
                int o2 = ((b2 & 3) << 6) | b3;
                out[op++] = (byte) o0;
                if (op < oLen) {
                    out[op++] = (byte) o1;
                }
                if (op < oLen) {
                    out[op++] = (byte) o2;
                }
            }
            return out;
        }

        private static int getLenValue(char[] in, int iOff, int iLen) {
            if (iLen % MOD != 0) {
                throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
            }
            while (iLen > 0 && in[iOff + iLen - 1] == CHAR_EQUAL) {
                iLen--;
            }
            return iLen;
        }

        private Base64Coder() {}
    }
}