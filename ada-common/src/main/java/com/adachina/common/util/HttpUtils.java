package com.adachina.common.util;


import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Common HttpUtils
 * 默认优先使用OkHttpClient 3实现，其次使用apache HttpClient实现（未实现）
 * 实现HttpDelegation接口以达到使用自己的Http库需求.<br/>
 * 前提classpath中并没有okhttp3.OkHttpClient和org.apache.http...CloseableHttpClient库。
 *
 * @author 蕉鹿
 * @date 2016/7/12
 */
@SuppressWarnings("unused")
public class HttpUtils {
    private final static String UNEXPECTED_CODE = "Unexpected code ";
    private HttpUtils() {
        //avoid be instanced
    }

    private static HttpDelegation http;
    private static final String OK_HTTP_CLIENT = "okhttp3.OkHttpClient";
    private static final String CLOSEABLE_HTTP_CLIENT = "org.apache.http.impl.client.CloseableHttpClient";
    static {
        HttpDelegation httpDelegation = null;
        if (ClassUtil.isPresent(OK_HTTP_CLIENT, HttpUtils.class.getClassLoader())) {
            httpDelegation = new OkHttpDelegation();
        } else if (ClassUtil.isPresent(CLOSEABLE_HTTP_CLIENT, HttpUtils.class.getClassLoader())) {
            // TODO 使用apache http-components实现
        } else {
            // TODO 底限：使用jdk HttpUrlConnection实现
        }
        http = httpDelegation;
    }

    public static String get(String url) {
        return http.get(url);
    }

    public static String get(String url, Map<String, String> queryParas) {
        return http.get(url, queryParas);
    }

    public static String post(String url, String data) {
        return http.post(url, data);
    }

    public static String postSSL(String url, String data, String certPath, String certPass) {
        return http.postSSL(url, data, certPath, certPass);
    }

    public static InputStream download(String url, String params) {
        return http.download(url, params);
    }

    public static String upload(String url, File file, String params) {
        return http.upload(url, file, params);
    }

    public interface HttpDelegation {

        /**
         * http GET request.
         *
         * @param url the request url
         * @return String the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        String get(String url);

        /**
         * http GET request with query-parameter.
         *
         * @param url        the request url
         * @param queryParas query-parameter
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        String get(String url, Map<String, String> queryParas);

        /**
         * http POST request with data.
         *
         * @param url  the request url.
         * @param data post data.
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        String post(String url, String data);

        /**
         * http POST SSL request with data.
         *
         * @param url      the request url.
         * @param data     post data.
         * @param certPath credential path.
         * @param certPass credential password.
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        String postSSL(String url, String data, String certPath, String certPass);

        /**
         * download file by http.
         *
         * @param url    the request url.
         * @param params request parameter.
         * @return InputStream.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        InputStream download(String url, String params);

        /**
         * upload file by http.
         *
         * @param url    the request url.
         * @param file   file.
         * @param params request parameter
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        String upload(String url, File file, String params);

    }

    public static class OkHttpDelegation implements HttpDelegation {

        private final MediaType CONTENT_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded");

        private OkHttpClient httpClient;

        public OkHttpDelegation() {
            httpClient = buildBasic(new OkHttpClient().newBuilder());
        }

        private OkHttpClient buildBasic(OkHttpClient.Builder builder) {
            return builder.connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        /**
         * http GET request.
         *
         * @param url the request url
         * @return String the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public String get(String url) {
            return exec(new Request.Builder().url(url).get().build());
        }

        /**
         * http GET request with query-parameter.
         *
         * @param url        the request url
         * @param queryParas query-parameter
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public String get(String url, Map<String, String> queryParas) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String, String> entry : queryParas.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            HttpUrl httpUrl = urlBuilder.build();
            return exec(new Request.Builder().url(httpUrl).get().build());
        }

        /**
         * http POST request with data.
         *
         * @param url  the request url.
         * @param data post data.
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public String post(String url, String data) {
            RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
            return exec(new Request.Builder().url(url).post(body).build());
        }

        /**
         * http POST SSL request with data.
         *
         * @param url      the request url.
         * @param data     post data.
         * @param certPath credential path.
         * @param certPass credential password.
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public String postSSL(String url, String data, String certPath, String certPass) {
            RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, data);
            Request request = new Request.Builder().url(url).post(body).build();

            try (InputStream inputStream = new FileInputStream(certPath)) {
                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                char[] passArray = certPass.toCharArray();
                clientStore.load(inputStream, passArray);

                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(clientStore, passArray);
                KeyManager[] kms = kmf.getKeyManagers();
                SSLContext sslContext = SSLContext.getInstance("TLSv1");
                sslContext.init(kms, null, new SecureRandom());
                OkHttpClient httpsClient = buildBasic(new OkHttpClient().newBuilder().sslSocketFactory(sslContext.getSocketFactory()));
                Response response = httpsClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new RuntimeException(UNEXPECTED_CODE + response);
                }
                return response.body().string();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * download file by http.
         *
         * @param url    the request url.
         * @param params request parameter.
         * @return InputStream.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public InputStream download(String url, String params) {
            Request request;
            if (StringUtils.isNotBlank(params)) {
                RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
                request = new Request.Builder().url(url).post(body).build();
            } else {
                request = new Request.Builder().url(url).get().build();
            }
            try {
                Response response = httpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new RuntimeException(UNEXPECTED_CODE + response);
                }
                return response.body().byteStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * upload file by http.
         *
         * @param url    the request url.
         * @param file   file.
         * @param params request parameter
         * @return String. the response content.
         * @author 蕉鹿
         * @date 2016.07.12
         */
        @Override
        public String upload(String url, File file, String params) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM).addFormDataPart("media", file.getName(), fileBody);
            if (StringUtils.isNotBlank(params)) {
                builder.addFormDataPart("description", params);
            }
            RequestBody requestBody = builder.build();
            return exec(new Request.Builder().url(url).post(requestBody).build());
        }
        /**
         * 执行请求
         * @param request
         * @return
         */
        private String exec(Request request) {
            try {
                Response response = httpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new RuntimeException(UNEXPECTED_CODE + response);
                }
                return response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
