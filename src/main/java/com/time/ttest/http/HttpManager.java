package com.time.ttest.http;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 03:16
 */
public abstract class HttpManager implements DefaultManager{

    @Getter @Setter
    private String defaultUserName;

    protected ConcurrentHashMap<String, Object> httpMap = new ConcurrentHashMap<>();

    public abstract void add(String userName, Object http);

    public abstract void add(String userName, Object http,Boolean main);

    public abstract Object get(String userName);

    @Override
    public void setDefaultUser(String userName) {
        this.defaultUserName = userName;
    }

    @Override
    public Object getDefaultUser() {
        return get(this.defaultUserName);
    }

}
