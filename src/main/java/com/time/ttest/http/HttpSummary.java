package com.time.ttest.http;

import kong.unirest.HttpMethod;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * http请求摘要
 * @Auther guoweijie

 * @Date 2020-03-03 12:06
 */
@Data
@Accessors(chain = true)
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

}
