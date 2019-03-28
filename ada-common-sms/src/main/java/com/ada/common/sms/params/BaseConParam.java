package com.ada.common.sms.params;

import com.ada.common.sms.constant.SmsApiMethodConstants;
import com.ada.common.sms.enums.MessageServiceEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private WangSuCloudConParam wangSuCloudConParam;

    private WelinkConParam welinkConParam;

    private BaseConParam() {

    }

    private static BaseConParam baseConParam;

    private static class BaseConParamFactory {
        private static BaseConParam baseConParam = new BaseConParam();
    }

    public static BaseConParam getBaseConParam () {

       return BaseConParamFactory.baseConParam;
    }

    public class WangSuCloudConParam {

        private WangSuCloudConParam() {

            init();
        }

        private void init(){

            api = "https://sms.server.matocloud.com/sms/is/api/sms/simple"+ SmsApiMethodConstants.WSC_API_METHOD_SENDVARSMS;
            authUser = "shadpz";
            userKey = "FUAkZ3TQugd9qZKZ";
            timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        }

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
        private String timestamp;

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
    }


    public class WelinkConParam {

        private WelinkConParam () {

            init();
        }
        private void init(){

            //todo
            api= "http://jiekou.51welink.com/submitdata/service.asmx" + SmsApiMethodConstants.Welink_API_METHOD_g_Submit;
            sname="dllxceshi";
            spwd="dllxceshi123";
//            scorpid= null;
            sprdid="1012888";
        }

        /**
         * api地址
         */
        private String api;
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

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSpwd() {
            return spwd;
        }

        public void setSpwd(String spwd) {
            this.spwd = spwd;
        }

        public String getScorpid() {
            return scorpid;
        }

        public void setScorpid(String scorpid) {
            this.scorpid = scorpid;
        }

        public String getSprdid() {
            return sprdid;
        }

        public void setSprdid(String sprdid) {
            this.sprdid = sprdid;
        }
    }


    public WangSuCloudConParam getWangSuCloudConParam() {

        if (wangSuCloudConParam == null) {

            synchronized (this) {
                if (wangSuCloudConParam == null) {
                    wangSuCloudConParam = new WangSuCloudConParam();
                }
            }
        }

        return wangSuCloudConParam;
    }

    public void setWangSuCloudConParam(WangSuCloudConParam wangSuCloudConParam) {
        this.wangSuCloudConParam = wangSuCloudConParam;
    }

    public WelinkConParam getWelinkConParam() {

        if (welinkConParam == null) {

            synchronized (this) {
                if (welinkConParam == null) {
                    welinkConParam = new WelinkConParam();
                }
            }
        }

        return welinkConParam;
    }

    public void setWelinkConParam(WelinkConParam welinkConParam) {
        this.welinkConParam = welinkConParam;
    }
}
