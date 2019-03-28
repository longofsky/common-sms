package com.ada.common.sms.service;

import com.ada.common.sms.params.AdaReqParam;
import com.ada.common.sms.params.AdaRespParam;

import java.io.IOException;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.um.sms.service
 * @ClassName: MessageService
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-25 15:45
 * @Version: 1.0
 */
public interface MessageService {


    /**
     * 短信发送接口
     * @description
     * @Author litianlong
     * @Version 1.0
     * @param adaReqParam
     * @return  AdaRespParam
     * @throws IOException
     * @Date 2019-03-27 13:42
     *
     */
    public AdaRespParam sendMessageContent(AdaReqParam adaReqParam) throws IOException;

}
