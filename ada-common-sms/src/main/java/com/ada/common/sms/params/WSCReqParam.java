package com.ada.common.sms.params;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
public class WSCReqParam extends AbstractSmsReqParam {

    public WSCReqParam (BaseConParam baseConParam,AdaReqParam adaReqParam) throws UnsupportedEncodingException {

        super(baseConParam,adaReqParam);
    }

    /**
     * api地址
     */
    private String api;

    private String parameter;

    private Map<String, String> headerValue;

    @Override
    public void init() throws UnsupportedEncodingException {

        Map<String, Object> smsParam = new HashMap<>(16);
        smsParam.put("type", adaReqParam.getType());

        /**
         * content 类型
         */
        smsParam.put("content", adaReqParam.getContent());
        /**
         * 变量模版类型
         */
        smsParam.put("templateId", adaReqParam.getTemplateId());

        smsParam.put("param", adaReqParam.getParam());
        smsParam.put("phone", adaReqParam.getPhone());
        //将smsParam转成json字符串
        final String smsJson = JSON.toJSONString(smsParam);

        //生成auth-signature
        Map<String, String> authSignatureParam = new HashMap<>();
        authSignatureParam.put("auth-timeStamp", baseConParam.getWangSuCloudConParam().getTimestamp());
        authSignatureParam.put("auth-user", baseConParam.getWangSuCloudConParam().getAuthUser());
        authSignatureParam.put("sms", smsJson);
        String authSignatureStr = generateSignData(authSignatureParam);
        authSignatureStr = authSignatureStr + "&userKey=" + baseConParam.getWangSuCloudConParam().getUserKey();
        final String sign = DigestUtils.md5Hex(authSignatureStr);

        //post请求头信息
        Map<String, String> header = new HashMap<>();
        header.put("auth-user", baseConParam.getWangSuCloudConParam().getAuthUser());
        header.put("auth-timeStamp", baseConParam.getWangSuCloudConParam().getTimestamp());
        header.put("auth-signature", sign);

        this.api = baseConParam.getWangSuCloudConParam().getApi();
        this.parameter = "sms=" + URLEncoder.encode(smsJson, StandardCharsets.UTF_8.name());
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

}
