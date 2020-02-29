package com.time.ttest.http;

import com.time.ttest.context.TTestApplicationContext;
import kong.unirest.HttpRequestSummary;
import kong.unirest.HttpResponseSummary;
import kong.unirest.MetricContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
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
        String status = "success";
        if (null != e){
            status = "fail";
           log.error("http error: {}",e.getMessage());
        }
        log.info("path: {} status: {} time: {}",requestSummary.getUrl(),status,(System.currentTimeMillis() - startTime));
    }
}
