package com.ada.common.sms.service;

import com.ada.common.sms.entitys.abstractentitys.AbstractAdaSmsReqParam;
import com.ada.common.sms.environment.BaseConnEnvironment;
import com.ada.common.sms.entitys.WelinkReqParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.service
 * @ClassName: WSCMessageServiceImpl
 * @Author: litianlong
 * @Description: 微网通连服务
 * @Date: 2019-03-27 13:42
 * @Version: 1.0
 */
public class WelinkMessageServiceImpl extends AbstractMessageService {

    private WelinkMessageServiceImpl () {

    }

    private static class WelinkMessageServiceImplFactory {
        private static WelinkMessageServiceImpl welinkMessageService = new WelinkMessageServiceImpl();
    }

    public static WelinkMessageServiceImpl getInstance () {

        BaseConnEnvironment.getBaseConnEnvironment().getWelinkConParam();

        return WelinkMessageServiceImplFactory.welinkMessageService;
    }

    @Override
    public Boolean sendMessageContent(AbstractAdaSmsReqParam adaReqParam) throws IOException {

        super.sendMessageContent(adaReqParam);

        WelinkReqParam welinkReqParam = new WelinkReqParam(baseConnEnvironment,adaReqParam);

        String result =  post(welinkReqParam);

        System.out.println(result);

        return null;

    }


    public static String post(WelinkReqParam welinkReqParam) {
        try {
            //发送POST请求
            URL url = new URL(welinkReqParam.getPostUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + welinkReqParam.getPostData().length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(welinkReqParam.getPostData());
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
