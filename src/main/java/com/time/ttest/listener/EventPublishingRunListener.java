package com.time.ttest.listener;

import com.time.ttest.TTestApplication;
import com.time.ttest.context.ConfigurableApplicationContext;
import com.time.ttest.context.TTestApplicationEventMulticaster;
import com.time.ttest.event.ApplicationRunningEvent;
import com.time.ttest.event.ApplicationStartedEvent;

public class EventPublishingRunListener implements ApplicationRunListener {

    private final TTestApplication application;
    private final TTestApplicationEventMulticaster applicationEventMulticaster;

    public EventPublishingRunListener(TTestApplication application) {
        this.application = application;
        this.applicationEventMulticaster = new TTestApplicationEventMulticaster();
        application.getListeners().forEach(applicationEventMulticaster::addApplicationListener);
    }


    @Override
    public void starting() {
        applicationEventMulticaster.multicastEvent(new ApplicationStartedEvent(this.application,null));
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        context.publishEvent(new ApplicationRunningEvent(this.application,context));
    }
}
