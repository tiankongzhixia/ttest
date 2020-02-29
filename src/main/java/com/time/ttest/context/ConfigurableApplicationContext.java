package com.time.ttest.context;

import com.time.ttest.listener.ApplicationListener;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void addApplicationListener(ApplicationListener<?> listener);

}
