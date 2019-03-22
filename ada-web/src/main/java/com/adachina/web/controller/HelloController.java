package com.adachina.web.controller;

import com.adachina.common.response.AdaResponse;
import com.adachina.common.util.I18nUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author ezbuy on ${date}
 */
@RestController
@Slf4j
@CrossOrigin
public class HelloController {


    @GetMapping("/hello")
    public AdaResponse hello() {

        String res = I18nUtils.getDefaultMessage("message.LOGIN_OK");
        log.info(res);
        AdaResponse response = AdaResponse.success(res);
        return response;
    }


    @PostMapping("/hello")
    public AdaResponse hello(@RequestBody String name) {
        String res = I18nUtils.getDefaultMessage("message.LOGIN_OK");
        AdaResponse response = AdaResponse.success(res);
        log.info(res);
        return response;
    }

}
