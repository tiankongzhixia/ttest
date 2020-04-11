package com.time.ttest.listener;

import com.time.ttest.context.TTestContext;

public interface ApplicationRunListener {
    void starting();

    void running(TTestContext context);
}
