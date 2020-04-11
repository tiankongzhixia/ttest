package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 20:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface GsonBean {
}
