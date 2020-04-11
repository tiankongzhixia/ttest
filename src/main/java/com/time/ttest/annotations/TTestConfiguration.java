package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 04:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface TTestConfiguration {
}
