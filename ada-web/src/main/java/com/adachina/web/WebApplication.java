package com.adachina.web;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.adachina.web.config.Test2Config;
import com.adachina.web.config.TestConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ezbuy on ${date}
 */
@SpringBootApplication(scanBasePackages = "com.adachina")
@EnableDiscoveryClient
@EnableApolloConfig
@RestController
@MapperScan("com.adachina.web.mapper")
public class WebApplication {

    @Bean
    public TestConfig testConfig() {
        return new TestConfig();
    }
    public static void main(String[] args){
        SpringApplication.run(WebApplication.class,args);
    }

    @GetMapping("/hello3")
    public int hello(){
        return testConfig().getTimeout();
    }

     @Autowired
     Test2Config test2Config;

    @GetMapping("/hello4")
    public String hello4(){
        return test2Config.getName();
    }

}
