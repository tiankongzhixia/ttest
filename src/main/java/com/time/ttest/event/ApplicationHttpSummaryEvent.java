package com.time.ttest.event;

import com.time.ttest.http.HttpSummary;

/**
 * http统计的消息
 * @Auther guoweijie
 * @Date 2020-03-03 12:49
 */
public class ApplicationHttpSummaryEvent extends AbsReportEvent {

    public ApplicationHttpSummaryEvent(HttpSummary httpSummary) {
        super(httpSummary);
    }
}
