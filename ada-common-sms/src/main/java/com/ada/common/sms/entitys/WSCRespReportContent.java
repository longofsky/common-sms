package com.ada.common.sms.entitys;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.entitys
 * @ClassName: WSCRespContent
 * @Author: litianlong
 * @Description: 网宿云获取短信发送状态接口返回参数
 * @Date: 2019-03-28 17:40
 * @Version: 1.0
 */
public class WSCRespReportContent  implements Serializable {


    /**
     * 短信处理批号
     */
    private String taskId;

    /**
     * 信息ID
     */
    private String msgId;
    /**
     * 原信息手机号
     */
    private String phone;
    /**
     * 处理状态
     */
    private String status;
    /**
     * 处理完成时间
     */
    private String doneTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
