package com.ada.common.sms.entitys;

import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.entitys
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public abstract class AbstractSmsReqParam {

    public BaseConnEnvironment baseConnEnvironment;

    public AdaReqParam adaReqParam;

    public AbstractSmsReqParam(BaseConnEnvironment baseConnEnvironment, AdaReqParam adaReqParam) throws UnsupportedEncodingException {

        this.baseConnEnvironment = baseConnEnvironment;
        this.adaReqParam = adaReqParam;
        init();
    }

    public abstract void init() throws UnsupportedEncodingException;
}
