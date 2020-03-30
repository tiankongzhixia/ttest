package com.time.ttest.http;

import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationHttpSummaryEvent;
import kong.unirest.HttpRequestSummary;
import kong.unirest.HttpResponseSummary;
import kong.unirest.MetricContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

/**
 * Unirest 拦截器
 * @Auther guoweijie
 * @Date 2020-02-25 17:15
 * 拦截http响应返回
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

    /**
     * 拦截http响应返回 将发送到http统计
     * @param responseSummary 返回结果
     * @param e 错误信息
     */
    @Override
    public void complete(HttpResponseSummary responseSummary, Exception e) {
        long endTime = System.currentTimeMillis();
        HttpSummary httpSummary = HttpSummaryFactory.builder()
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setDuration(endTime - startTime)
                .setExceptionMessage(null != e?e.getMessage():null)
                .setStatus(responseSummary.getStatus())
                .setStatusText(responseSummary.getStatusText())
                .setUrl(requestSummary.getUrl())
                .setMethod(requestSummary.getHttpMethod());
        context.publishEvent(new ApplicationHttpSummaryEvent(httpSummary));
    }
}
