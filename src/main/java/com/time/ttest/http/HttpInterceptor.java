package com.time.ttest.http;

import kong.unirest.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther guoweijie

 * @Date 2020-02-25 14:31
 */
@Slf4j
public class HttpInterceptor implements Interceptor {

    public void onRequest(HttpRequest<?> request, Config config) {
//        log.info("http request url {}",request.getBody());
    }

    public void onResponse(HttpResponse<?> response, HttpRequestSummary request, Config config) {
//        log.info("http response url {} ,status {}",request.getUrl(),response.getStatus());
    }

    public HttpResponse<?> onFail(Exception e, HttpRequestSummary request, Config config) throws UnirestException {
        throw new UnirestException(e);
    }
}
