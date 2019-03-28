package com.ada.sms.params;

import com.ada.sms.constant.SmsApiMethodConstants;
import com.ada.sms.enums.MessageServiceEnum;
import com.ada.sms.service.WSCMessageServiceImpl;
import com.ada.sms.service.WelinkMessageServiceImpl;

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

    static MessageServiceEnum serviceEnum;

    private WangSuCloudConParam wangSuCloudConParam;

    private WelinkConParam welinkConParam;

    private BaseConParam() {

        /**
         * 初始化运营商基础数据
         */
        if (serviceEnum != null) {

            if (MessageServiceEnum.WSC.equals(serviceEnum)) {
                wangSuCloudConParam = new WangSuCloudConParam();

            } else if (MessageServiceEnum.WELINk.equals(serviceEnum)) {

                welinkConParam = new WelinkConParam();
            }

        }
    }

    private static BaseConParam baseConParam;

    private static class BaseConParamFactory {
        private static BaseConParam baseConParam = new BaseConParam();
    }

    public static BaseConParam getBaseConParam (MessageServiceEnum messageServiceEnum) {

        if (messageServiceEnum != null) {

            serviceEnum = messageServiceEnum;
        }

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
        return wangSuCloudConParam;
    }

    public void setWangSuCloudConParam(WangSuCloudConParam wangSuCloudConParam) {
        this.wangSuCloudConParam = wangSuCloudConParam;
    }

    public WelinkConParam getWelinkConParam() {
        return welinkConParam;
    }

    public void setWelinkConParam(WelinkConParam welinkConParam) {
        this.welinkConParam = welinkConParam;
    }
}
