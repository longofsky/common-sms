package com.adachina.common.util;

/**
 * @author jack Created by jack
 */
public class StringHelper {
    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
