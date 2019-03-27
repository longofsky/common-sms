package com.ada.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Author: jlq
 * Date: 2015/12/7
 * Time: 14:40
 * 解压缩工具.
 * @author alexzhu
 */
public class CompressUtil {
    public static final Logger log = LoggerFactory.getLogger(CompressUtil.class);

    private CompressUtil() {
        throw new IllegalStateException("CompressUtil Utility class");
    }

    /**
     * 压缩.
     * @param srcBytes 要压缩的字节数组.
     * @return byte[]
     * @throws Exception
     */
    public static byte[] compress(byte[] srcBytes) throws Exception {
        if (srcBytes == null || srcBytes.length == 0) {
            return new byte[]{};
        }
        byte[] compressResult;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(srcBytes);
            gzip.finish();
            gzip.flush();
            compressResult = out.toByteArray();
        } catch (Exception e) {
            log.error("compress error.", e);
            throw new RuntimeException(e);
        }
        return compressResult;
    }

    /**
     * 解压缩.
     * @param srcBytes 要解压缩的字节数组.
     * @return byte[]
     * @throws Exception
     */
    public static byte[] uncompress(byte[] srcBytes) throws Exception {
        if (srcBytes == null || srcBytes.length == 0) {
            return new byte[]{};
        }
        byte[] compressResult;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(srcBytes);
             GZIPInputStream gzip = new GZIPInputStream(in)) {
            int n;
            byte[] buffer = new byte[256];
            while ((n = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            compressResult = out.toByteArray();
        } catch (Exception e) {
            log.error("uncompress error.", e);
            throw new RuntimeException(e);
        }
        return compressResult;
    }

}
