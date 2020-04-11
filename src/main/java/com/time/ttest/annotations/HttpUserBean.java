package com.time.ttest.annotations;

import com.time.ttest.http.HttpClientType;

import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 03:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface HttpUserBean {

    /**
     * http登录用户名
     * @return
     */
    String value();

    /**
     * 是否默认
     * @return
     */
    boolean isMain() default false;

}
