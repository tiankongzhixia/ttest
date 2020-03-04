package com.time.ttest.http;

import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationHttpSummaryEvent;
import kong.unirest.HttpRequestSummary;
import kong.unirest.HttpResponseSummary;
import kong.unirest.MetricContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

/**
 * @Auther guoweijie

 * @Date 2020-02-25 17:15
 * 发出实际请求后的那一刻
 */
@Slf4j
public class HttpMetricContext implements MetricContext {
    private Long startTime;
    private HttpRequestSummary requestSummary;
    private TTestApplicationContext context;

    public HttpMetricContext(Long startTime, HttpRequestSummary requestSummary, TTestApplicationContext context) {
        this.startTime = startTime;
        this.requestSummary = requestSummary;
        this.context = context;
    }

    @Override
    public void complete(HttpResponseSummary responseSummary, Exception e) {
        long endTime = System.currentTimeMillis();
        HttpSummary httpSummary = HttpSummaryFactory.build()
                .startTime(startTime)
                .endTime(endTime)
                .duration(endTime - startTime)
                .exception(null != e?e.getMessage():null)
                .status(responseSummary.getStatus())
                .statusText(responseSummary.getStatusText())
                .url(requestSummary.getUrl())
                .method(requestSummary.getHttpMethod());
        context.publishEvent(new ApplicationHttpSummaryEvent(httpSummary));
    }
}
