package com.time.ttest.context;

import com.time.ttest.listener.ApplicationListener;

public interface TTestContext extends ApplicationContext {

    void addApplicationListener(ApplicationListener<?> listener);

}
