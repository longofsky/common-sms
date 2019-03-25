package com.adachina.um.sms.factory;

import com.adachina.um.sms.service.MessageService;
import com.adachina.um.sms.service.MessageServiceSupport;

/**
 * @ProjectName: adachina-commons
 * @Package: com.adachina.um.sms.factory
 * @ClassName: MessageServiceFactory
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-25 15:47
 * @Version: 1.0
 */
public class MessageServiceFactory {

    public static MessageService getMobileMessageService(){
        return new MessageServiceSupport(){
            @Override
            public String getType() {
                return MessageServiceSupport.PHONO_MESSAGE_TYPE;
            }
        };
    }
}
