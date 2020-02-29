package com.time.ttest.http;

import com.time.ttest.context.TTestApplicationContext;
import kong.unirest.HttpRequestSummary;
import kong.unirest.MetricContext;
import kong.unirest.UniMetric;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther guoweijie

 * @Date 2020-02-25 17:14
 * 提出实际要求之前的那一刻
 */
@Slf4j
public class HttpUniMetric implements UniMetric {
    private TTestApplicationContext context;
    public HttpUniMetric(TTestApplicationContext context) {
        this.context = context;
    }

    @Override
    public MetricContext begin(HttpRequestSummary requestSummary) {
        long nonTime = System.currentTimeMillis();
        log.info("request start path: {} method: {}",requestSummary.getUrl(),requestSummary.getHttpMethod().name());
        return new HttpMetricContext(nonTime, requestSummary,context);
    }
}
