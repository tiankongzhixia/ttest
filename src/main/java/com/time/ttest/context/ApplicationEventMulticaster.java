package com.time.ttest.context;

import com.time.ttest.event.ApplicationEvent;
import com.time.ttest.listener.ApplicationListener;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);
    void removeApplicationListener(ApplicationListener<?> listener);
    void removeAllListeners();
    void multicastEvent(ApplicationEvent event);

}
