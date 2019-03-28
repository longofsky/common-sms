package com.ada.common.sms.entitys.abstractentitys;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.entitys
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: 短信服务商 共有参数
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public abstract class AbstractAdaSmsReqParam {

    /**
     * 短信内容
     */
    public String content;
    /**
     * 接收手机号
     */
    public String phone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
