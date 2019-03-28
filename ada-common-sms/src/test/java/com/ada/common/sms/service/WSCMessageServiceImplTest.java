package com.ada.common.sms.service;


import com.ada.common.sms.entitys.WSCAdaSmsReqParam;
import com.ada.common.sms.enums.MessageServiceEnum;
import com.ada.common.sms.factory.MessageServiceFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @ProjectName: ada-sms
 * @Package: com.ada.common.sms.service
 * @ClassName: WSCMessageServiceImplTest
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-28 16:04
 * @Version: 1.0
 */
public class WSCMessageServiceImplTest {


    @Test
    public void testSendMessageContent () throws IOException {


        WSCAdaSmsReqParam adaReqParam = new WSCAdaSmsReqParam();

        adaReqParam.setType(2);
        adaReqParam.setContent("【Ada健康】您的验证码是：25129。请不要把验证码泄露给其他人");

        String [] param = new String[2];
        param[0] = "121247";
        param[1] = "2";
        adaReqParam.setParam(param);

        adaReqParam.setPhone("15527248869");
//        adaReqParam.setTemplateId("55005");

        MessageService messageService = MessageServiceFactory.getMessageService(MessageServiceEnum.WSC);

        messageService.sendMessageContent(adaReqParam);

    }
}
