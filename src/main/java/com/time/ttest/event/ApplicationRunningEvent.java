package com.time.ttest.event;

import com.time.ttest.TTestApplication;
import com.time.ttest.context.ConfigurableApplicationContext;
import lombok.Getter;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
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
