package com.ada.sms.params;

import java.util.Arrays;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.params
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public class AdaReqParam {


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String[] getParam() {
        return param;
    }

    public void setParam(String[] param) {
        this.param = param;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "AdaReqParam{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", templateId='" + templateId + '\'' +
                ", param=" + Arrays.toString(param) +
                ", phone='" + phone + '\'' +
                '}';
    }
}
