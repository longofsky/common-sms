package com.ada.common.sms.service;

import com.ada.common.sms.entitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.entitys.AdaRespParam;
import com.ada.common.sms.environment.BaseConnEnvironment;

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


    public BaseConnEnvironment baseConnEnvironment;

    public AbstractMessageService() {

        baseConnEnvironment = BaseConnEnvironment.getBaseConnEnvironment();

    }

    @Override
    public AdaRespParam sendMessageContent(AbstractAdaSmsReqParam adaReqParam) throws IOException  {

        /**
         * 异步持久化短信信息 todo
         */
        return null;
    }
}
