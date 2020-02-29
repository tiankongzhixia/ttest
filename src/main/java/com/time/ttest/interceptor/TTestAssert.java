package com.time.ttest.interceptor;

import lombok.Data;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-28 05:19
 */
@Data
public class TTestAssert {

    public TTestAssert(String path, Object expected,Class transform) {
        this.path = path;
        this.expected = expected;
        this.transform = transform;
    }

    private String path;

    private Object expected;

    private Class transform;
}
