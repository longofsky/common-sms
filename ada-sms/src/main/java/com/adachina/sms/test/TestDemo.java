package com.adachina.sms.test;


import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @ProjectName: microservice-spring-cloud
 * @Package: com.adachina.sms.test
 * @ClassName: TestDemo
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-26 10:40
 * @Version: 1.0
 */
public class TestDemo {

    public static void main(String[] args) throws IOException {

//        final String api = "https://sms.server.matocloud.com/sms/is/api/sms/simple/sendSms";
        final String api = "https://sms.server.matocloud.com/sms/is/api/sms/simple/sendVarSms";
        final String user = "shadpz";
        final String userKey = "FUAkZ3TQugd9qZKZ";
        final String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, Object> smsParam = new HashMap<>();
        smsParam.put("type", 2);

        /**
         * content 类型
         */
//        smsParam.put("content", "【Ada健康】爱达医疗短信测试。");
        /**
         * 变量模版类型
         */
        smsParam.put("templateId", "55005");

        String [] param = new String[2];
        param[0] = "121247";
        param[1] = "2";

        smsParam.put("param", param);
        smsParam.put("phone", "15527248869");
        //将smsParam转成json字符串
        final String smsJson = JSON.toJSONString(smsParam);

        //生成auth-signature
        Map<String, String> authSignatureParam = new HashMap<>();
        authSignatureParam.put("auth-timeStamp", timestamp);
        authSignatureParam.put("auth-user", user);
        authSignatureParam.put("sms", smsJson);
        String authSignatureStr = TestDemo.generateSignData(authSignatureParam);
        authSignatureStr = authSignatureStr + "&userKey=" + userKey;//末尾加上userKey
        final String sign = DigestUtils.md5Hex(authSignatureStr);

        //post请求头信息
        Map<String, String> header = new HashMap<>();
        header.put("auth-user", user);
        header.put("auth-timeStamp", timestamp);
        header.put("auth-signature", sign);
        String responseJson = TestDemo.post(api, "sms=" + URLEncoder.encode(smsJson, StandardCharsets.UTF_8.name()), header);
        System.out.println(responseJson);
    }

    public static String post(String requestURL, String parameter, Map<String, String> headerValue) throws IOException {
        StringBuilder result = new StringBuilder();

        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL url = new URL(requestURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            if (MapUtils.isNotEmpty(headerValue)) {
                Set<String> headerKeySet = headerValue.keySet();
                for (String headerKey : headerKeySet) {
                    httpURLConnection.setRequestProperty(headerKey, headerValue.get(headerKey));
                }
            }

            if (StringUtils.isNotBlank(parameter)) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(parameter.getBytes());
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
