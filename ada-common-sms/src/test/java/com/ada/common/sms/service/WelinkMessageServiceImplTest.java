package com.ada.common.sms.service;

import com.ada.common.sms.entitys.WelinkAdaSmsReqParam;
import com.ada.common.sms.enums.MessageServiceEnum;
import com.ada.common.sms.factory.MessageServiceFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.service
 * @ClassName: WelinkMessageServiceImplTest
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-28 16:05
 * @Version: 1.0
 */
public class WelinkMessageServiceImplTest {

    @Test
    public void testSendMessageContent () throws IOException {

        WelinkAdaSmsReqParam adaReqParam = new WelinkAdaSmsReqParam();
        adaReqParam.setContent("您的验证码是：25129。请不要把验证码泄露给其他人。【微网通联】");

        adaReqParam.setPhone("15527248869");

//        MessageService messageService = MessageServiceFactory.getMessageService(MessageServiceEnum.WELINk);
//
//        messageService.sendMessageContent(adaReqParam);

    }
}
