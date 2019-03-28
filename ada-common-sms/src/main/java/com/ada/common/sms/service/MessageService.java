package com.ada.common.sms.service;

import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.entitys.AdaRespParam;

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
     * @return  Boolean
     * @throws IOException
     * @Date 2019-03-27 13:42
     *
     */
    public Boolean sendMessageContent(AbstractAdaSmsReqParam adaReqParam) throws IOException;


    /**
     * @param
     * @return
     * @throws
     * @Author litianlong
     * @Version 1.0
     * @Date 2019-03-28 18:22
     */
    public void getSmsReport () throws IOException;


}
