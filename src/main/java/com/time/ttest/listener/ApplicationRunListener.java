package com.time.ttest.listener;

import com.time.ttest.context.ConfigurableApplicationContext;

public interface ApplicationRunListener {
    void starting();

    void running(ConfigurableApplicationContext context);
}
