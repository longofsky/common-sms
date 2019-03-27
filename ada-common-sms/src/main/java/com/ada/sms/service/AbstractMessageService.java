package com.ada.sms.service;

import com.ada.sms.params.AdaReqParam;
import com.ada.sms.params.AdaRespParam;
import com.ada.sms.params.BaseConParam;
import com.ada.sms.params.WelinkReqParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

        baseConParam = BaseConParam.getBaseConParam(null);

    }

    @Override
    public AdaRespParam send(AdaReqParam adaReqParam) throws IOException  {

        /**
         * 异步持久化短信信息 todo
         */
        return null;
    }
}
