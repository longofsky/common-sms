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
public class WelinkReqParam extends AbstractSmsReqParam{



    public WelinkReqParam (BaseConParam baseConParam,AdaReqParam adaReqParam) throws UnsupportedEncodingException {
        super(baseConParam,adaReqParam);
    }

    /**
     * api地址
     */
    private String postData;

    private String postUrl;

    @Override
    public void init() throws UnsupportedEncodingException {

        StringBuilder postDataBuilder = new StringBuilder();
        postDataBuilder.append("sname=");

        if (StringUtils.isNotEmpty(baseConParam.getWelinkConParam().getSname())) {
            postDataBuilder.append(baseConParam.getWelinkConParam().getSname());
        }
        postDataBuilder.append("&spwd=");
        if (StringUtils.isNotEmpty(baseConParam.getWelinkConParam().getSpwd())) {
            postDataBuilder.append(baseConParam.getWelinkConParam().getSpwd());
        }
        postDataBuilder.append("&scorpid=");
        if (StringUtils.isNotEmpty(baseConParam.getWelinkConParam().getScorpid())) {
            postDataBuilder.append(baseConParam.getWelinkConParam().getScorpid());
        }
        postDataBuilder.append("&sprdid=");
        if (StringUtils.isNotEmpty(baseConParam.getWelinkConParam().getSprdid())) {
            postDataBuilder.append(baseConParam.getWelinkConParam().getSprdid());
        }
        postDataBuilder.append("&sdst=");
        if (StringUtils.isNotEmpty(adaReqParam.getPhone())) {
            postDataBuilder.append(adaReqParam.getPhone());
        }
        postDataBuilder.append("&smsg=");
        if (StringUtils.isNotEmpty(adaReqParam.getContent())) {
            postDataBuilder.append(java.net.URLEncoder.encode(adaReqParam.getContent()));
        }
        this.postData = postDataBuilder.toString();
        this.postUrl = baseConParam.getWelinkConParam().getApi();
    }

    public String getPostData() {
        return postData;
    }

    public String getPostUrl() {
        return postUrl;
    }

}
