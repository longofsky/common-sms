package com.adachina.web.util;

import com.adachina.common.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author create by jack
 */
public class EntityUtils {

    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    public static <T> void setCreateInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = "";
        if (request != null) {
            userId = String.valueOf(request.getHeader("userId"));
        }
        String[] fields = {"createUser", "createTime"};
        Object[] value = new Object[]{userId, new Date()};
        setDefaultValues(entity, fields, value);
    }

    public static <T> void setUpdatedInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = "";
        if (request != null) {
            userId = String.valueOf(request.getHeader("userId"));
        }

        String[] fields = {"updateUser", "updateTime"};
        Object[] value = new Object[]{userId, new Date()};
        ;
        setDefaultValues(entity, fields, value);
    }

    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}
