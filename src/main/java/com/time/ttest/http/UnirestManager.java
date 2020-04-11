package com.time.ttest.http;

import com.google.inject.Singleton;
import kong.unirest.Config;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 03:17
 */
@Singleton
public class UnirestManager extends HttpManager {

    @Override
    public void add(String userName, Object config) {
        add(userName, config,false);
    }

    @Override
    public void add(String userName, Object config, Boolean main) {
        super.httpMap.putIfAbsent(userName, new UnirestInstance((Config) config));
        if (main) setDefaultUser(userName);
    }

    @Override
    public Object get(String userName) {
        return httpMap.getOrDefault(userName,Unirest.primaryInstance());
    }
}
