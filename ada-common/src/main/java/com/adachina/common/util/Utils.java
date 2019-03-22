package com.adachina.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/**
 * 工具类
 * 
 * @author fanglin
 *
 */
public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	private static final String CHARSET_UTF8 = "UTF-8";

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(CHARSET_UTF8));

		} catch (Exception e) {
			logger.error("Md5加密器初始化异常：", e);
			throw new RuntimeException("Md5加密器初始化异常");
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();

	}

	public static String serialize(Object obj) {
		String serStr = null;
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, CHARSET_UTF8);
		} catch (Exception e) {
			logger.error("将Object序列化时出现异常：", e);
		}
		return serStr;
	}

	public static Object deserialization(String str) {
		Object newObj = null;
		try (
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
						java.net.URLDecoder.decode(str, CHARSET_UTF8).getBytes("ISO-8859-1"));
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
			newObj = objectInputStream.readObject();
		} catch (Exception e) {
			logger.error("将Object反序列化时出现异常：", e);
		}
		return newObj;
	}

	private Utils() {
		throw new IllegalStateException("Utility class");
	}
}
