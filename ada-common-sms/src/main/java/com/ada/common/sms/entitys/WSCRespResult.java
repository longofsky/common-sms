package com.ada.common.sms.entitys;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.entitys
 * @ClassName: WSCResp
 * @Author: litianlong
 * @Description: 网宿云发送短信接口返回参数
 * @Date: 2019-03-28 17:40
 * @Version: 1.0
 */
public class WSCRespResult  implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 信息ID
     */
    private String msgId;

    /**
     * 状态描述
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
