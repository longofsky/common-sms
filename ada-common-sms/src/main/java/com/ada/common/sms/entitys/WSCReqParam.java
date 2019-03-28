package com.ada.common.sms.entitys;

import com.ada.common.sms.constant.SmsApiMethodConstants;
import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.entitys.abstractentitys.AbstractSmsReqParam;
import com.ada.common.sms.environment.BaseConnEnvironment;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.entitys
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: 网宿云接口请求参数
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public class WSCReqParam extends AbstractSmsReqParam  implements Serializable {

    private WSCAdaSmsReqParam wscAdaSmsReqParam;

    public WSCReqParam (BaseConnEnvironment baseConnEnvironment, AbstractAdaSmsReqParam abstractAdaSmsReqParam) throws UnsupportedEncodingException {
        this.baseConnEnvironment = baseConnEnvironment;
        this.wscAdaSmsReqParam = (WSCAdaSmsReqParam)abstractAdaSmsReqParam;

        init();
    }

    /**
     * api地址
     */
    private String api;

    private String parameter;

    private Map<String, String> headerValue;

    @Override
    public void init() throws UnsupportedEncodingException {

        //生成auth-signature
        Map<String, String> authSignatureParam = new HashMap<>();

        if (wscAdaSmsReqParam == null) {
            this.api = baseConnEnvironment.getWangSuCloudConParam().getApi() + SmsApiMethodConstants.WSC_API_METHOD_getSmsReport;
        } else {

            Map<String, Object> smsParam = new HashMap<>(16);
            smsParam.put("type", wscAdaSmsReqParam.getType());


            /**
             * 变量模版类型
             */

            if (StringUtils.isNotBlank(wscAdaSmsReqParam.getTemplateId())) {

                smsParam.put("templateId", wscAdaSmsReqParam.getTemplateId());
                this.api = baseConnEnvironment.getWangSuCloudConParam().getApi() + SmsApiMethodConstants.WSC_API_METHOD_SENDVARSMS;
            } else {

                if (StringUtils.isNotBlank(wscAdaSmsReqParam.getContent())) {
                    /**
                     * content 类型
                     */
                    smsParam.put("content", wscAdaSmsReqParam.getContent());
                } else {
                    throw new RuntimeException("消息模版ID和信息内容不能同时为空！");
                }
                this.api = baseConnEnvironment.getWangSuCloudConParam().getApi() + SmsApiMethodConstants.WSC_API_METHOD_SENDSMS;
            }
            smsParam.put("param", wscAdaSmsReqParam.getParam());
            smsParam.put("phone", wscAdaSmsReqParam.getPhone());
            //将smsParam转成json字符串
            final String smsJson = JSON.toJSONString(smsParam);

            authSignatureParam.put("sms", smsJson);
            this.parameter = "sms=" + URLEncoder.encode(smsJson, StandardCharsets.UTF_8.name());
        }

        authSignatureParam.put("auth-timeStamp", baseConnEnvironment.getWangSuCloudConParam().getTimestamp());
        authSignatureParam.put("auth-user", baseConnEnvironment.getWangSuCloudConParam().getAuthUser());

        String authSignatureStr = generateSignData(authSignatureParam);
        authSignatureStr = authSignatureStr + "&userKey=" + baseConnEnvironment.getWangSuCloudConParam().getUserKey();
        final String sign = DigestUtils.md5Hex(authSignatureStr);

        //post请求头信息
        Map<String, String> header = new HashMap<>();
        header.put("auth-user", baseConnEnvironment.getWangSuCloudConParam().getAuthUser());
        header.put("auth-timeStamp", baseConnEnvironment.getWangSuCloudConParam().getTimestamp());
        header.put("auth-signature", sign);

        this.headerValue = header;

    }

    public String getApi() {
        return api;
    }

    public String getParameter() {
        return parameter;
    }

    public Map<String, String> getHeaderValue() {
        return headerValue;
    }


    public  String generateSignData(Map<String, String> params) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keys.add(entry.getKey());
        }
        //按参数名从小到大排序
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        List<String> data = new ArrayList<>();
        for (String key : keys) {
            data.add(key + "=" + params.get(key));
        }
        return StringUtils.join(data, "&");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
