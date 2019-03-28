package com.ada.common.sms.entitys;

import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.entitys.abstractentitys.AbstractSmsReqParam;
import com.ada.common.sms.environment.BaseConnEnvironment;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.entitys
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public class WelinkReqParam extends AbstractSmsReqParam {

    private WelinkAdaSmsReqParam welinkAdaSmsReqParam;

    public WelinkReqParam (BaseConnEnvironment baseConnEnvironment, AbstractAdaSmsReqParam abstractAdaSmsReqParam) throws UnsupportedEncodingException {
        this.baseConnEnvironment = baseConnEnvironment;
        this.welinkAdaSmsReqParam = (WelinkAdaSmsReqParam)abstractAdaSmsReqParam;

        init();
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

        if (StringUtils.isNotEmpty(baseConnEnvironment.getWelinkConParam().getSname())) {
            postDataBuilder.append(baseConnEnvironment.getWelinkConParam().getSname());
        }
        postDataBuilder.append("&spwd=");
        if (StringUtils.isNotEmpty(baseConnEnvironment.getWelinkConParam().getSpwd())) {
            postDataBuilder.append(baseConnEnvironment.getWelinkConParam().getSpwd());
        }
        postDataBuilder.append("&scorpid=");
        if (StringUtils.isNotEmpty(baseConnEnvironment.getWelinkConParam().getScorpid())) {
            postDataBuilder.append(baseConnEnvironment.getWelinkConParam().getScorpid());
        }
        postDataBuilder.append("&sprdid=");
        if (StringUtils.isNotEmpty(baseConnEnvironment.getWelinkConParam().getSprdid())) {
            postDataBuilder.append(baseConnEnvironment.getWelinkConParam().getSprdid());
        }
        postDataBuilder.append("&sdst=");
        if (StringUtils.isNotEmpty(welinkAdaSmsReqParam.getPhone())) {
            postDataBuilder.append(welinkAdaSmsReqParam.getPhone());
        }
        postDataBuilder.append("&smsg=");
        if (StringUtils.isNotEmpty(welinkAdaSmsReqParam.getContent())) {
            postDataBuilder.append(java.net.URLEncoder.encode(welinkAdaSmsReqParam.getContent(),StandardCharsets.UTF_8.name()));
        }
        this.postData = postDataBuilder.toString();
        this.postUrl = baseConnEnvironment.getWelinkConParam().getApi();
    }

    public String getPostData() {
        return postData;
    }

    public String getPostUrl() {
        return postUrl;
    }

}
