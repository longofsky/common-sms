package com.adachina.web.config;

import org.springframework.stereotype.Component;

/**
 * @author ezbuy on ${date}
 */
@Component
public class Test2Refresh extends ApolloRefreshConfig<Test2Config> {

    private final static String START_STR = "tt";

    @Override
    boolean startsWith(String source) {
        return source.startsWith(START_STR);
    }
}
