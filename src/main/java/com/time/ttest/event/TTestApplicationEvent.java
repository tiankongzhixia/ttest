package com.time.ttest.event;

import com.time.ttest.TTestApplication;

public abstract class TTestApplicationEvent extends ApplicationEvent {

    public TTestApplicationEvent(TTestApplication application) {
        super(application);
    }
}
