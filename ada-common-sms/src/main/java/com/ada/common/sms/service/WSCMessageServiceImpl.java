package com.ada.common.sms.service;

import com.ada.common.sms.entitys.WSCResp;
import com.ada.common.sms.entitys.WSCRespReport;
import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.environment.BaseConnEnvironment;
import com.ada.common.sms.entitys.WSCReqParam;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: 网速运服务
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public class WSCMessageServiceImpl extends AbstractMessageService {

    private WSCMessageServiceImpl () {

    }


    private static final Integer SENDMESSAGECONTENT_TRUE = 1;

    private static final Integer SENDMESSAGECONTENT_FALSE = 0;


    private static class WSCMessageServiceImplFactory {
        private static WSCMessageServiceImpl wscMessageService = new WSCMessageServiceImpl();
    }

    public static WSCMessageServiceImpl getInstance () {

        BaseConnEnvironment.getBaseConnEnvironment().getWangSuCloudConParam();

        return WSCMessageServiceImplFactory.wscMessageService;
    }

    @Override
    public Boolean sendMessageContent(AbstractAdaSmsReqParam adaReqParam) throws IOException {

        super.sendMessageContent(adaReqParam);
        WSCReqParam wscReqParam = new WSCReqParam(baseConnEnvironment,adaReqParam);

        String result = post(wscReqParam);

        System.out.println(result);

        WSCResp wscResp = JSON.parseObject(result, WSCResp.class);

        if (wscResp == null) {

            return false;
        }
        if (SENDMESSAGECONTENT_TRUE.equals(wscResp.getReturnCode())) {

            return true;
        }

        return false;
    }
    @Override
    public WSCRespReport getSmsReport () throws IOException {

        WSCReqParam wscReqParam = new WSCReqParam(baseConnEnvironment,null);

        String result = post(wscReqParam);

        System.out.println(result);

        return JSON.parseObject(result, WSCRespReport.class);


    }

    private static String post(WSCReqParam wscReqParam) throws IOException {
        StringBuilder result = new StringBuilder();

        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL url = new URL(wscReqParam.getApi());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            if (MapUtils.isNotEmpty(wscReqParam.getHeaderValue())) {
                Set<String> headerKeySet = wscReqParam.getHeaderValue().keySet();
                for (String headerKey : headerKeySet) {
                    httpURLConnection.setRequestProperty(headerKey, wscReqParam.getHeaderValue().get(headerKey));
                }
            }

            if (StringUtils.isNotBlank(wscReqParam.getParameter())) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(wscReqParam.getParameter().getBytes());
                outputStream.flush();
                outputStream.close();
            }

            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            String lineStr = null;
            byte[] buf = new byte[4096];
            int bytesRead = 0;
            while (bytesRead >= 0) {
                lineStr = new String(buf, 0, bytesRead, StandardCharsets.UTF_8);
                result.append(lineStr);
                bytesRead = bufferedInputStream.read(buf);
            }
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }

        return result.toString();
    }

}
