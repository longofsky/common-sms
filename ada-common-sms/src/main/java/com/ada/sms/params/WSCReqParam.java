package com.ada.sms.params;

import java.util.Map;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.params
 * @ClassName: WSCReqParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:51
 * @Version: 1.0
 */
public class WSCReqParam {


    /**
     * api地址
     */
    private String api;

    private String parameter;

    private Map<String, String> headerValue;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Map<String, String> getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(Map<String, String> headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public String toString() {
        return "WSCReqParam{" +
                "api='" + api + '\'' +
                ", parameter='" + parameter + '\'' +
                ", headerValue=" + headerValue +
                '}';
    }
}
