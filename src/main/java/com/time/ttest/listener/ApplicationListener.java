package com.time.ttest.listener;

import com.time.ttest.event.ApplicationEvent;

import java.util.EventListener;

public interface ApplicationListener<T extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(T event);
}
