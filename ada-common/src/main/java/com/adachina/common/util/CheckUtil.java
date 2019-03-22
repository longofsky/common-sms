package com.adachina.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author mengxianming-2015年11月12日
 */
public class CheckUtil {

    /**
     * private constructor
     */
    private CheckUtil() {
        throw new IllegalStateException("CheckUtil Utility class");
    }

    /**
     * 判断对象是否为空。
     * 空对象可以为：
     * null、空字符串、长度为0数组、size()为0的集合类、size()为0的MAP。
     *
     * @param obj
     * @return
     * @author mengxianming-2016年2月29日
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return "".equals(obj);
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

}
