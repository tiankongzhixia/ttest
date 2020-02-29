package com.time.ttest.event;

import com.time.ttest.TTestApplication;
import com.time.ttest.context.ConfigurableApplicationContext;
import lombok.Getter;

public class ApplicationStartedEvent extends TTestApplicationEvent {
    @Getter
    private final ConfigurableApplicationContext configurableApplicationContext;

    public ApplicationStartedEvent(TTestApplication application, ConfigurableApplicationContext configurableApplicationContext) {
        super(application);
        this.configurableApplicationContext = configurableApplicationContext;
    }
}
