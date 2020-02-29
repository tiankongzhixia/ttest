package com.time.ttest.context;

import com.time.ttest.event.ApplicationEvent;

public interface ApplicationEventPublisher {

    default void publishEvent(ApplicationEvent event) {
        this.publishEvent((Object)event);
    }

    void publishEvent(Object event);
}
