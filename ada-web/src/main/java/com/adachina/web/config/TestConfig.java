package com.adachina.web.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author ezbuy on ${date}
 */


public class TestConfig {

    @Value("${timeout:88}")
    private int timeout;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
