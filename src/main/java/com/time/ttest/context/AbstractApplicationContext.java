package com.time.ttest.context;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.time.ttest.event.ApplicationEvent;
import com.time.ttest.listener.ApplicationListener;

import java.util.LinkedHashSet;
import java.util.Set;

@Singleton
public abstract class AbstractApplicationContext implements TTestContext {

    private final Set<ApplicationListener<?>> applicationListeners;
    private ApplicationEventMulticaster applicationEventMulticaster;

    public AbstractApplicationContext() {
        this.applicationListeners = new LinkedHashSet<>();
    }

    private ApplicationEventMulticaster getApplicationEventMulticaster(){
        if (applicationEventMulticaster == null){
            this.applicationEventMulticaster = this.getInjector().getInstance(TTestApplicationEventMulticaster.class);
        }
        return this.applicationEventMulticaster;
    }

    public void publishEvent(Object event) {
        if (event instanceof ApplicationEvent){
            ApplicationEvent applicationEvent = (ApplicationEvent) event;
            this.getApplicationEventMulticaster().multicastEvent(applicationEvent);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener){
        this.getApplicationEventMulticaster().addApplicationListener(listener);
        this.applicationListeners.add(listener);
    }

    public abstract Injector getInjector();

}
