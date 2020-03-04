package com.time.ttest.http;

import kong.unirest.HttpMethod;
import lombok.Data;

/**
 * http请求摘要
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-03 12:06
 */
@Data
public class HttpSummary{

    private String url;


    private HttpMethod method;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 状态
     */
    private int status;

    /**
     * 状态名称
     */
    private String statusText;

    /**
     * 错误信息
     */
    private String exceptionMessage;

    /**
     * 持续时间
     */
    private long duration;

    public HttpSummary url(String url){
        this.url = url;
        return this;
    }

    public HttpSummary method(HttpMethod method){
        this.method = method;
        return this;
    }

    public HttpSummary startTime(long startTime){
        this.startTime = startTime;
        return this;
    }

    public HttpSummary endTime(long endTime){
        this.endTime = endTime;
        return this;
    }

    public HttpSummary status(int status){
        this.status = status;
        return this;
    }

    public HttpSummary statusText(String statusText){
        this.statusText = statusText;
        return this;
    }

    public HttpSummary exception(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public HttpSummary duration(long duration){
        this.duration = duration;
        return this;
    }



}
