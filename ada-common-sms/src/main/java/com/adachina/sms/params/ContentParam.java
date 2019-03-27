package com.adachina.sms.params;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.params
 * @ClassName: BaseConParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:35
 * @Version: 1.0
 */
public class ContentParam {

    /**
     * 网速运
     */

    /**
     * 短信类型 type:业务类型 ，必填，整型， 1-营销短信，2-行业短信【比如验证码】
     */
    private Integer type;

    /**
     * 短信内容
     */
    private String content;
    /**
     * 模版ID
     */
    private String templateId;
    /**
     * 模版参数
     */
    private String [] param;
    /**
     * 接收手机号
     */
    private String phone;

    /**
     * 微网通联
     */

    /**
     * 短信通道
     */
    private String sprdid;

    /**
     * 手机号
     */
    private String sdst;

    /**
     * 短信内容
     */
    private String smsg;

}
