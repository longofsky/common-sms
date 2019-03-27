package com.adachina.sms.params;

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


    private class WangSuCloudConParam {

        /**
         *
         */
        private String api;
        /**
         *
         */
        private String authUser;
        /**
         *
         */
        private String userKey;
        /**
         *
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

    }


    private class WelinkConParam {

    }

}
