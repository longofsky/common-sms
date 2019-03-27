package com.ada.sms.params;

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
public class WelinkReqParam extends AbstractSmsReqParam{



    public WelinkReqParam (BaseConParam baseConParam,AdaReqParam adaReqParam) {
        super(baseConParam,adaReqParam);
    }

    @Override
    public void init() throws UnsupportedEncodingException {
        postData = "sname="+baseConParam.getWelinkConParam().getSname()+"&spwd="+baseConParam.getWelinkConParam().getSpwd()+"&scorpid="+baseConParam.getWelinkConParam().getScorpid()+"&sprdid="+baseConParam.getWelinkConParam().getSprdid()+"&sdst="+adaReqParam.getPhone()+"&smsg="+java.net.URLEncoder.encode(adaReqParam.getContent(),"utf-8");

        postUrl = baseConParam.getWelinkConParam().getApi();
    }

    /**
     * api地址
     */
    private String postData;

    private String postUrl;

    public String getPostData() {
        return postData;
    }

    public String getPostUrl() {
        return postUrl;
    }

}
