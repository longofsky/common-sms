package com.ada.sms.params;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.params
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public abstract class AbstractSmsReqParam {

    public BaseConParam baseConParam;

    public AdaReqParam adaReqParam;

    public AbstractSmsReqParam(BaseConParam baseConParam, AdaReqParam adaReqParam) {

        this.baseConParam = baseConParam;
        this.adaReqParam = adaReqParam;
    }

    public abstract void init() throws UnsupportedEncodingException;
}
