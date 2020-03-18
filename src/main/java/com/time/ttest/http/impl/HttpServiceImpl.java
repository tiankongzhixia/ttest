package com.time.ttest.http.impl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.time.ttest.http.HttpService;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.JsonPatchRequest;
import kong.unirest.UnirestInstance;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServiceImpl implements HttpService {

    private final Provider<UnirestInstance> unirestInstanceProvider;


    @Inject
    public HttpServiceImpl(Provider<UnirestInstance> unirestInstanceProvider) {
        this.unirestInstanceProvider = unirestInstanceProvider;
    }
    ;
    public GetRequest get(String url) {
        return unirestInstanceProvider.get().get(url);
    }

    public GetRequest head(String url) {
        return unirestInstanceProvider.get().head(url);
    }

    public GetRequest options(String url) {
        return unirestInstanceProvider.get().options(url);
    }

    public HttpRequestWithBody post(String url) {
        return unirestInstanceProvider.get().post(url);
    }

    public HttpRequestWithBody delete(String url) {
        return unirestInstanceProvider.get().delete(url);
    }

    public HttpRequestWithBody patch(String url) {
        return unirestInstanceProvider.get().patch(url);
    }

    public HttpRequestWithBody put(String url) {
        return unirestInstanceProvider.get().put(url);
    }

    public JsonPatchRequest jsonPatch(String url) {
        return unirestInstanceProvider.get().jsonPatch(url);
    }

    public HttpRequestWithBody request(String method, String url) {
        return unirestInstanceProvider.get().request(method, url);
    }
}
