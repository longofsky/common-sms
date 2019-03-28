package com.ada.common.sms.entitys;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.entitys
 * @ClassName: WSCResp
 * @Author: litianlong
 * @Description: 网宿云获取短信发送状态接口返回参数
 * @Date: 2019-03-28 17:40
 * @Version: 1.0
 */
public class WSCRespReport  implements Serializable {

    /**
     * 接口处理状态吗 1：成功，0：失败
     */
    private String returnCode;

    /**
     * 接口处理状态描述
     */
    private String returnMsg;

    /**
     * 相关内容
     */
    private List<WSCRespReportContent> content;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<WSCRespReportContent> getContent() {
        return content;
    }

    public void setContent(List<WSCRespReportContent> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
