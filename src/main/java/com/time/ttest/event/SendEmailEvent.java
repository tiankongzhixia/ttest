package com.time.ttest.event;

import com.time.ttest.TTestApplication;

/**
 * @Auther guoweijie

 * @Date 2020-02-23 18:15
 */
public class SendEmailEvent extends TTestApplicationEvent {

    public SendEmailEvent(TTestApplication application) {
        super(application);
    }
}
