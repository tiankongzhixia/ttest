package com.time.ttest.event;

import com.time.ttest.report.TestNGReport;

/**
 * 应用测试报告的消息
 * @Auther guoweijie
 * @Date 2020-02-26 20:42
 */
public class ApplicationReportEvent extends AbsReportEvent {

    public ApplicationReportEvent(TestNGReport source) {
        super(source);
    }
}
