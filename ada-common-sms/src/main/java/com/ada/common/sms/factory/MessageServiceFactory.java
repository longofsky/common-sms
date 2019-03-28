package com.ada.common.sms.factory;

import com.ada.common.sms.enums.MessageServiceEnum;
import com.ada.common.sms.service.MessageService;
import com.ada.common.sms.service.WSCMessageServiceImpl;
import com.ada.common.sms.service.WelinkMessageServiceImpl;

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

        if (MessageServiceEnum.WSC.equals(messageServiceEnum)) {

            return WSCMessageServiceImpl.getInstance();

        } else if (MessageServiceEnum.WELINk.equals(messageServiceEnum)) {

            return WelinkMessageServiceImpl.getInstance();
        }

        return null;
    }

}
