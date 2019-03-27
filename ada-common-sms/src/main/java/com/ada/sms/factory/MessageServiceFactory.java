package com.ada.sms.factory;

import com.ada.sms.enums.MessageServiceEnum;
import com.ada.sms.params.BaseConParam;
import com.ada.sms.service.MessageService;
import com.ada.sms.service.WSCMessageServiceImpl;
import com.ada.sms.service.WelinkMessageServiceImpl;

/**
 * @ProjectName: ada-sms
 * @Package: com.adachina.um.sms.factory
 * @ClassName: MessageServiceFactory
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-25 15:47
 * @Version: 1.0
 */
public class MessageServiceFactory {

    public static MessageService getMessageService(MessageServiceEnum messageServiceEnum) {

        /**
         * 初始化 BaseConParam
         */
        BaseConParam.getBaseConParam(messageServiceEnum);

        if (MessageServiceEnum.WSC.equals(messageServiceEnum)) {

            return WSCMessageServiceImpl.getInstance();

        } else if (MessageServiceEnum.WELINk.equals(messageServiceEnum)) {

            return WelinkMessageServiceImpl.getInstance();
        }

        return null;
    }

}
