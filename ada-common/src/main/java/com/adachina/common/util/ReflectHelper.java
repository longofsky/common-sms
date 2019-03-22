package com.adachina.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
* @Description: 反射工具
* @author alex (90167)
* @date 2014-1-10 下午3:59:40
 */
public class ReflectHelper {
	private static final Logger logger = LoggerFactory.getLogger(ReflectHelper.class);

	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @author alex (90167)
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				 return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				logger.debug("reflect exception : {}", e);
			}
		}
		logger.warn("在类{}中未找到对应属性{}，直接返回null",obj.getClass(),fieldName);
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @throws IllegalAccessException
	 * @author alex (90167)
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) throws IllegalAccessException{
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if(field != null){
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws IllegalAccessException
	 * @author alex (90167)
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
                                           Object value) throws  IllegalAccessException,NoSuchFieldException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
	
	/**
	 * 将obj对象所有有值的属性值拼接起来作为字符串返回
	 * @param obj
	 * @throws IllegalAccessException
	 * @author pxc (91559)
	 */
	public static String getFieldValuesByObject(Object obj)
			throws IllegalAccessException {
		Class<?> orgObj = obj.getClass();
		Field[] cFields = orgObj.getDeclaredFields();
		StringBuilder response = null;
		Object value = null;
		if (cFields == null || cFields.length == 0) {
			return null;
		}
		response = new StringBuilder();
		for (Field field : cFields) {
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
				if (value != null) {
					response.append(field.getName()).append("-").append(value).append("-");
				}
			}
		}
		String responseValue = null;
		if (response.length() > 0) {
			responseValue = response.substring(0, response.length() - 1).trim().replaceAll("[^\\w|^\\d]", "_");
		}
		return responseValue;
	}

	private ReflectHelper() {
	}
}
