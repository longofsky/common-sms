package com.ada.sms.service;

import com.ada.sms.enums.MessageServiceEnum;
import com.ada.sms.params.AdaReqParam;
import com.ada.sms.params.AdaRespParam;

import java.io.IOException;

/**
 * @ProjectName: adachina-commons
 * @Package: com.adachina.um.sms.service
 * @ClassName: MessageService
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-25 15:45
 * @Version: 1.0
 */
public interface MessageService {


    /**
     * @Description
     * @Author litianlong
     * @Version 1.0
     * @Param
     * @Return
     * @Exception
     * @Date 2019-03-27 13:42
     */
    public AdaRespParam send(AdaReqParam adaReqParam) throws IOException;

}
