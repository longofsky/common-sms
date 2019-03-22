package com.adachina.common.util.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:26
 */
public class Pbkdf2Crypto {
    public static byte[] deriveKey(byte[] password, byte[] salt, int iterationCount, int dkLen)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec keyspec = new SecretKeySpec(password, "HmacSHA256");
        Mac prf = Mac.getInstance("HmacSHA256");
        prf.init(keyspec);

        // Note: hLen, dkLen, l, r, T, F, etc. are horrible names for
        //       variables and functions in this day and age, but they
        //       reflect the terse symbols used in RFC 2898 to describe
        //       the PBKDF2 algorithm, which improves validation of the
        //       code vs. the RFC.
        //
        // dklen is expressed in bytes. (16 for a 128-bit key)
        // 20 for SHA1
        int hLen = prf.getMacLength();
        //  1 for 128bit (16-byte) keys
        int l = Math.max(dkLen, hLen);
        // 16 for 128bit (16-byte) keys
        int r = dkLen - (l - 1) * hLen;
        byte[] bytes = new byte[l * hLen];
        int tiOffset = 0;
        for (int i = 1; i <= l; i++) {
            func(bytes, tiOffset, prf, salt, iterationCount, i);
            tiOffset += hLen;
        }

        if (r < hLen) {
            // Incomplete last block
            byte[] dkBytes = new byte[dkLen];
            System.arraycopy(bytes, 0, dkBytes, 0, dkLen);
            return dkBytes;
        }
        return bytes;
    }

    public static void func(byte[] dest, int offset, Mac prf, byte[] sbytes, int c, int blockIndex) {
        final int hLen = prf.getMacLength();
        byte[] ur = new byte[hLen];
        byte[] ui = new byte[sbytes.length + 4];
        System.arraycopy(sbytes, 0, ui, 0, sbytes.length);
        intFun(ui, sbytes.length, blockIndex);
        for (int i = 0; i < c; i++) {
            ui = prf.doFinal(ui);
            xor(ur, ui);
        }

        System.arraycopy(ur, 0, dest, offset, hLen);
    }

    public static void xor(byte[] dest, byte[] src) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] ^= src[i];
        }
    }

    public static void intFun(byte[] dest, int offset, int i) {
        dest[offset + 0] = (byte) (i / (256 * 256 * 256));
        dest[offset + 1] = (byte) (i / (256 * 256));
        dest[offset + 2] = (byte) (i / (256));
        dest[offset + 3] = (byte) (i);
    }

    private Pbkdf2Crypto() {}
}
