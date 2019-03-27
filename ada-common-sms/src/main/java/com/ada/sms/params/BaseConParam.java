package com.ada.sms.params;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.sms.params
 * @ClassName: BaseConParam
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-27 10:35
 * @Version: 1.0
 */
public class BaseConParam {

    public class WangSuCloudConParam {

        /**
         * api地址
         */
        private String api;
        /**
         * 用户名
         */
        private String authUser;
        /**
         * 密码
         */
        private String userKey;
        /**
         * 时间戳
         */
        private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        /**
         *
         */
        private Map<String, Object> smsParam = new HashMap<>();
        /**
         *
         */
        private String smsJson;

        private Map<String, String> authSignatureParam;


        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getAuthUser() {
            return authUser;
        }

        public void setAuthUser(String authUser) {
            this.authUser = authUser;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Map<String, Object> getSmsParam() {
            return smsParam;
        }

        public void setSmsParam(Map<String, Object> smsParam) {
            this.smsParam = smsParam;
        }

        public String getSmsJson() {
            return smsJson;
        }

        public void setSmsJson(String smsJson) {
            this.smsJson = smsJson;
        }

        public Map<String, String> getAuthSignatureParam() {
            return authSignatureParam;
        }

        public void setAuthSignatureParam(Map<String, String> authSignatureParam) {
            this.authSignatureParam = authSignatureParam;
        }
    }


    public class WelinkConParam {

        /**
         * 用户名
         */
        private String sname;

        /**
         * 密码
         */
        private String spwd;

        private String scorpid;

        /**
         * 产品类型
         */
        private String sprdid;
    }



}
