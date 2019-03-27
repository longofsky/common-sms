package com.ada.sms.service;

import com.ada.sms.enums.MessageServiceEnum;
import com.ada.sms.params.AdaReqParam;
import com.ada.sms.params.AdaRespParam;
import com.ada.sms.params.BaseConParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: microservice-spring-cloud
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public class WelinkMessageServiceImpl implements MessageService {


    private BaseConParam baseConParam;
    private WelinkMessageServiceImpl () {

        /**
         * 初始化baseConParam todo
         */
        baseConParam = new BaseConParam();

    }

    private static class WelinkMessageServiceImplFactory {
        private static WelinkMessageServiceImpl welinkMessageService = new WelinkMessageServiceImpl();
    }

    public static WelinkMessageServiceImpl getInstance () {

        return WelinkMessageServiceImplFactory.welinkMessageService;
    }

    @Override
    public AdaRespParam send(AdaReqParam adaReqParam) {

        return null;

    }


    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
}
