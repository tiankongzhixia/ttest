package com.time.ttest.event;

import com.time.ttest.TTestApplication;
import com.time.ttest.context.ConfigurableApplicationContext;
import lombok.Getter;

/**
 * 应用装载完Guice后发送消息
 * @Auther guoweijie
 * @Date 2020-02-23 17:00
 */
public class ApplicationRunningEvent extends TTestApplicationEvent {
    @Getter
    private final ConfigurableApplicationContext configurableApplicationContext;

    public ApplicationRunningEvent(TTestApplication application,  ConfigurableApplicationContext configurableApplicationContext) {
        super(application);
        this.configurableApplicationContext = configurableApplicationContext;
    }
}
