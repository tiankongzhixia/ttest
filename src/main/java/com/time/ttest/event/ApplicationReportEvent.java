package com.time.ttest.event;

import com.time.ttest.report.TestNGReport;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 20:42
 */
public class ApplicationReportEvent extends ApplicationEvent {

    public ApplicationReportEvent(TestNGReport source) {
        super(source);
    }
}
