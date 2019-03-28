package com.ada.common.sms.entitys;

/**
 * @ProjectName: microservice-spring-cloud
 * @Package: com.ada.common.sms.entitys
 * @ClassName: WSCResp
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-28 17:40
 * @Version: 1.0
 */
public class WSCRespResult {


    private Integer code;

    private String phone;

    private String msgId;

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
        return "WSCRespResult{" +
                "code=" + code +
                ", phone='" + phone + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
