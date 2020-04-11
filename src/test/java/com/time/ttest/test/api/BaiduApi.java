package com.time.ttest.test.api;

import com.google.inject.Inject;
import com.time.ttest.annotations.HttpUser;
import com.time.ttest.http.HttpService;
import kong.unirest.Headers;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 14:04
 */
public class BaiduApi {

    @Inject
    private HttpService<UnirestInstance> httpService;
    private String baseUrl = "http://baidu.com";

    /**
     * 上层控制访问用户
     * @return
     */
    public Headers getHomePageRequestHeaders(){
        return httpService.start().get(baseUrl).getHeaders();
    }

    /**
     * 传参控制访问用户
     * @param userName
     * @return
     */
    @HttpUser("{userName}")
    public Headers getHomePageRequestHeaders(String userName){
        return httpService.start().get(baseUrl).getHeaders();
    }
}
