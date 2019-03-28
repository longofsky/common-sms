package com.ada.common.sms.entitys;

import com.ada.common.sms.environment.BaseConnEnvironment;

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


    public abstract void init() throws UnsupportedEncodingException;
}
