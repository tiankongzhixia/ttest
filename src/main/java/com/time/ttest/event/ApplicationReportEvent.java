package com.time.ttest.event;

import com.time.ttest.report.TestNGReport;

/**
 * @Auther guoweijie

 * @Date 2020-02-26 20:42
 */
public class ApplicationReportEvent extends AbsReportEvent {

    public ApplicationReportEvent(TestNGReport source) {
        super(source);
    }
}
