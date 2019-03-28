package com.ada.common.sms.service;

import com.ada.common.sms.params.AdaReqParam;
import com.ada.common.sms.params.AdaRespParam;
import com.ada.common.sms.params.BaseConParam;

import java.io.IOException;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public abstract class AbstractMessageService implements MessageService {


    public BaseConParam baseConParam;

    public AbstractMessageService() {

        baseConParam = BaseConParam.getBaseConParam();

    }

    @Override
    public AdaRespParam sendMessageContent(AdaReqParam adaReqParam) throws IOException  {

        /**
         * 异步持久化短信信息 todo
         */
        return null;
    }
}
