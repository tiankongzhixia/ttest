package com.time.ttest.http;

import lombok.Getter;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 03:42
 */
public enum HttpClientType {

    UNIREST(UnirestManager.class);

    @Getter
    Class<? extends HttpManager> managerClass;

    HttpClientType(Class<? extends HttpManager> managerClass) {
        this.managerClass = managerClass;
    }
}
