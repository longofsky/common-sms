package com.ada.common.sms.entitys;

import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.entitys
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: 以网宿云为服务封装的短信接口入参
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public class WSCAdaSmsReqParam extends AbstractAdaSmsReqParam  implements Serializable {

    /**
     * 短信类型 type:业务类型 ，必填，整型， 1-营销短信，2-行业短信【比如验证码】
     */
    private Integer type;

    /**
     * 模版ID
     */
    private String templateId;
    /**
     * 模版参数
     */
    private String [] param;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
