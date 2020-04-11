package com.time.ttest.annotations;

import com.time.ttest.http.HttpClientType;

import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 04:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface EnableHttpManager {

    HttpClientType value() default HttpClientType.UNIREST;
}
