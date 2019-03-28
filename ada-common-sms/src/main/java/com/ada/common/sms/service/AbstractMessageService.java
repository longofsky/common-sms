package com.ada.common.sms.service;

import com.ada.common.sms.entitys.WSCRespReport;
import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.environment.BaseConnEnvironment;

import java.io.IOException;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: 短信服务定义抽象类
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public abstract class AbstractMessageService implements MessageService {


    public BaseConnEnvironment baseConnEnvironment;

    public AbstractMessageService() {

        baseConnEnvironment = BaseConnEnvironment.getBaseConnEnvironment();

    }

    @Override
    public Boolean sendMessageContent(AbstractAdaSmsReqParam adaReqParam) throws IOException  {

        /**
         * 异步持久化短信信息 todo
         */
        return null;
    }

    @Override
    public WSCRespReport getSmsReport () throws IOException {

        return null;
    }
}
