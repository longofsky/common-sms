package com.adachina.web.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author create by jack
 */
public class I18nUtils {

    private static final String MSG_BASE = "i18n/messages";
    private static final String ERROR_BASE = "i18n/errors";
    private static final String VALID_BASE = "i18n/ValidationMessages";

    /**
     * 国际化信息，缓存不同语言类型的国际化属性配置文件
     */
    private static final ConcurrentMap<String, ResourceBundle> MESSAGES = new ConcurrentHashMap<String, ResourceBundle>();

    /**
     * 国际化信息
     *
     * @param key    系统语言类型
     * @param params 属性中待替换的参数
     * @return
     */
    public static String getDefaultMessage(String key, Object... params) {
        return getMessage(MSG_BASE, key, params);
    }

    /**
     * 国际化信息
     *
     * @param locale 系统语言类型
     * @param key    key
     * @param params 属性中待替换的参数
     * @return
     */
    public static String getMessage(Locale locale, String key, Object... params) {
        return getMessage(locale, MSG_BASE, key, params);
    }

    /**
     * 国际化信息
     *
     * @param locale 系统语言类型
     * @param key    key
     * @param params 属性中待替换的参数
     * @return
     */
    public static String getValidMsg(Locale locale, String key, Object... params) {
        return getMessage(locale, VALID_BASE, key, params);
    }

    /**
     * 国际化信息
     *
     * @param key    key
     * @param params 属性中待替换的参数
     * @return
     */
    public static String getDefaultError(String key, Object... params) {
        return getMessage(ERROR_BASE, key, params);
    }

    /**
     * 国际化信息
     *
     * @param locale 系统语言类型
     * @param key    key
     * @param params 属性中待替换的参数
     * @return
     */
    public static String getError(Locale locale, String key, Object... params) {
        return getMessage(locale, ERROR_BASE, key, params);
    }

    /**
     * 国际化信息
     *
     * @param baseName 国际化属性文件路径，比如“i18n/messages”
     * @param key      系统语言类型
     * @param params   属性中待替换的参数
     * @return
     */
    private static String getMessage(String baseName, String key, Object... params) {
        // 获取系统语言环境
        Locale locale = LocaleContextHolder.getLocale();

        // 根据不同的语言类型，获取对应的属性配置文件
        ResourceBundle message = MESSAGES.get(baseName + "_" + locale.toString());
        if (message == null) {
            // 配置国际化配置文件路径
            message = ResourceBundle.getBundle(baseName, locale);
            MESSAGES.put(baseName + "_" + locale.toString(), message);
        }

        // 获取属性值
        String value = message.getString(key);
        // 替换属性值中的占位符
        if (params != null && params.length > 0) {
            value = String.format(value, params);
        }
        return value;
    }

    /**
     * 国际化信息
     *
     * @param locale   系统语言类型
     * @param baseName 国际化属性文件路径，比如“i18n/messages”
     * @param key      key
     * @param params   属性中待替换的参数
     * @return
     */
    private static String getMessage(Locale locale, String baseName, String key, Object... params) {

        String language = locale.getLanguage();

        // 根据不同的语言类型，获取对应的属性配置文件
        ResourceBundle message = MESSAGES.get(baseName + "_" + language);
        if (message == null) {
            // 配置国际化配置文件路径
            message = ResourceBundle.getBundle(baseName, locale);
            MESSAGES.put(baseName + "_" + language, message);
        }

        // 获取属性值
        String value = message.getString(key);
        // 替换属性值中的占位符
        if (params != null && params.length > 0) {
            value = String.format(value, params);
        }
        return value;
    }


    /**
     * 清除国际化信息
     */
    public static void flushMessage() {
        MESSAGES.clear();
    }
}
