package com.ada.common.sms.constant;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.sms.constant
 * @ClassName: SmsApiMethodConstants
 * @Author: litianlong
 * @Description: 业务商发送短信方法
 * @Date: 2019-03-27 17:26
 * @Version: 1.0
 */
public class SmsApiMethodConstants {

    /**
     * 网宿云发送普通短信
     */
    public static final String WSC_API_METHOD_SENDSMS = "/sendSms";
    /**
     * 网宿云发送模版参数短信
     */
    public static final String WSC_API_METHOD_SENDVARSMS = "/sendVarSms";
    /**
     * 网宿云获取发送短信状态
     */
    public static final String WSC_API_METHOD_getSmsReport = "/getSmsReport";

    /**
     * 微网通联发送普通短信
     */
    public static final String Welink_API_METHOD_g_Submit = "/g_Submit";
}
