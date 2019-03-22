package com.adachina.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * 重试工具.
 *
 * @author 蕉鹿
 * @date 2016/7/12
 */
public class RetryUtils {

    private static final Logger logger = LoggerFactory.getLogger(RetryUtils.class);

    /**
     * 结果是可校验的.
     *
     * @author 蕉鹿
     * @date 2016/7/12
     */
    public interface Inspective {

        /**
         * 校验结果有效性
         *
         * @return true is available, else false.
         */
        boolean isAvailable();

        /**
         * 返回结果JSON格式字符串
         *
         * @return json字符串
         */
        String toJson();
    }

    /**
     * 如果获取结果发生异常，重新获取结果.
     *
     * @param retryTimes 重试次数
     * @param callable   获取结果回调
     * @param <R>        结果
     * @return 结果实例
     * @exception RuntimeException
     * @author 蕉鹿
     * @date 2016.07.12
     */
    public static <R extends Inspective> R retryOnException(int retryTimes, Callable<R> callable) {
        R result = null;
        for (int i = 0; i < retryTimes; i++) {
            try {
                result = callable.call();
            } catch (Exception e) {
                logger.warn("retry on " + (i + 1) + " times v = " + (result == null ? null : result.toJson()), e);
            }
            if (result != null && result.isAvailable()) {
                break;
            }
        }
        return result;
    }

    /**
     * 如果获取结果发生异常，重新获取结果.
     *
     * @param retryTimes 重试次数
     * @param timeout    每次重试后睡眠时间.(毫秒)
     * @param callable   获取结果回调
     * @param <R>        结果
     * @return 结果实例
     * @exception RuntimeException
     * @author 蕉鹿
     * @date 2016.07.12
     */
    public static <R extends Inspective> R retryOnException(int retryTimes, long timeout, Callable<R> callable) throws InterruptedException {
        R result = null;
        for (int i = 0; i < retryTimes; i++) {
            try {
                result = callable.call();
            } catch (Exception e) {
                logger.warn("retry on " + (i + 1) + " times v = " + (result == null ? null : result.toJson()), e);
            }
            if (result != null && result.isAvailable()) {
                break;
            }
            TimeUnit.SECONDS.sleep(timeout);
        }
        return result;
    }


}
