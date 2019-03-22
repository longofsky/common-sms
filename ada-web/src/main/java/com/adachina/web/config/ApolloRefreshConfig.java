package com.adachina.web.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.adachina.common.util.AopTargetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;

/**
 * @author ezbuy on ${date}
 */
@Slf4j
public abstract class ApolloRefreshConfig<T> {

    @Autowired
    private T t;

    @Autowired
    private RefreshScope refreshScope;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean redisCacheKeysChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (startsWith(changedKey)) {
                redisCacheKeysChanged = true;
                break;
            }
        }
        if (!redisCacheKeysChanged) {
            return;
        }

        log.info("before refresh {}", t);
        refreshScope.refresh(StringUtils.uncapitalize((AopTargetUtils.getTarget(t)).getClass().getSimpleName()));
        log.info("after refresh {}", t);
    }


    /**
     *
     * @param source
     * @return
     */
    abstract boolean startsWith(String source);
}
