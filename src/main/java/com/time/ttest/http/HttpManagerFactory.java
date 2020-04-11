package com.time.ttest.http;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 04:45
 */
@Singleton
public class HttpManagerFactory {

    @Setter @Getter
    public HttpClientType httpClientType;

    @Inject
    public  Injector injector;

    public HttpManager getHttpManager(){
       return injector.getInstance(httpClientType.getManagerClass());
    }
}
