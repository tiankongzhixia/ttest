package com.time.ttest.event;

import com.time.ttest.TTestApplication;
import com.time.ttest.context.TTestContext;
import lombok.Getter;

/**
 * 应用装载Guice前发送
 */
public class ApplicationStartedEvent extends TTestApplicationEvent {
    @Getter
    private final TTestContext TTestContext;

    public ApplicationStartedEvent(TTestApplication application, TTestContext TTestContext) {
        super(application);
        this.TTestContext = TTestContext;
    }
}
