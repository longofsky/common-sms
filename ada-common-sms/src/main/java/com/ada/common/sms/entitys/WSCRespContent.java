package com.ada.common.sms.entitys;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.entitys
 * @ClassName: WSCRespContent
 * @Author: litianlong
 * @Description: 网宿云发送短信接口返回参数
 * @Date: 2019-03-28 17:40
 * @Version: 1.0
 */
public class WSCRespContent  implements Serializable {

    /**
     * 任务批号
     */
    private String taskId;
    /**
     * 内容
     */
    private List<WSCRespResult> result;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<WSCRespResult> getResult() {
        return result;
    }

    public void setResult(List<WSCRespResult> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
