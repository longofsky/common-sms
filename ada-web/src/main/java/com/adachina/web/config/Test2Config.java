package com.adachina.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author ezbuy on ${date}
 */

@ConfigurationProperties("tt")
@Data
@RefreshScope
@Component("test2Config")
public class Test2Config {


    private String name;

    private String password;


}
