package com.ada.sms.service;

import com.ada.sms.params.AdaReqParam;
import com.ada.sms.params.AdaRespParam;
import com.ada.sms.params.BaseConParam;
import com.ada.sms.params.WSCReqParam;
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
 * @ProjectName: microservice-spring-cloud
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public class WSCMessageServiceImpl implements MessageService {

    private BaseConParam baseConParam;

    private WSCMessageServiceImpl () {

        /**
         * 初始化baseConParam todo
         */
        baseConParam = new BaseConParam();
    }

    private static class WSCMessageServiceImplFactory {
        private static WSCMessageServiceImpl baseConParam = new WSCMessageServiceImpl();
    }

    public static WSCMessageServiceImpl getInstance () {

        return WSCMessageServiceImplFactory.baseConParam;
    }

    @Override
    public AdaRespParam send(AdaReqParam adaReqParam) throws IOException {

        /**
         * 异步持久化短信信息 todo
         */

        /**
         * 根据baseConParam 和 adaReqParam 封装wscReqParam
         */
        WSCReqParam wscReqParam = new WSCReqParam();

        String result = post(wscReqParam);

        /**
         * 记录短信批次
         */

        return null;
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

    public static String generateSignData(Map<String, String> params) {
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
