package com.ada.common.sms.test;

import com.ada.common.sms.enums.MessageServiceEnum;
import com.ada.common.sms.factory.MessageServiceFactory;
import com.ada.common.sms.service.MessageService;
import com.ada.common.sms.entitys.AdaReqParam;

import java.io.IOException;

/**
 * @ProjectName: microservice-spring-cloud
 * @Package: com.ada.sms.test
 * @ClassName: AdaSmsTest
 * @Author: litianlong
 * @Description: ${description}
 * @Date: 2019-03-28 10:16
 * @Version: 1.0
 */
public class AdaSmsTest {


    public static void main(String[] args) throws IOException {

        AdaReqParam adaReqParam = new AdaReqParam();

        adaReqParam.setType(2);
        adaReqParam.setContent("您的验证码是：25129。请不要把验证码泄露给其他人。【微网通联】");

        String [] param = new String[2];
        param[0] = "121247";
        param[1] = "2";
        adaReqParam.setParam(param);

        adaReqParam.setPhone("15527248869");
        adaReqParam.setTemplateId("55005");


        MessageService messageService = MessageServiceFactory.getMessageService(MessageServiceEnum.WSC);
//        MessageService messageService = MessageServiceFactory.getMessageService(MessageServiceEnum.WELINk);

        messageService.sendMessageContent(adaReqParam);

    }
}
