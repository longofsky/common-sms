package com.ada.sms.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ProjectName: microservice-spring-cloud
 * @Package: com.adachina.sms.test
 * @ClassName: welinkSMSTest
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-26 16:18
 * @Version: 1.0
 */
public class welinkSMSTest {


    public static void main(String[] args) throws IOException {


        String PostData = "sname=dllxceshi&spwd=dllxceshi123&scorpid=&sprdid=1012888&sdst=15527248869&smsg="+java.net.URLEncoder.encode("您的验证码是：25129。请不要把验证码泄露给其他人。【微网通联】","utf-8");

        String ret = SMS(PostData, "http://jiekou.51welink.com/submitdata/service.asmx/g_Submit");

        System.out.println(ret);

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
