package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * mybatis 多个mappper扫描
 * @Auther guoweijie
 * @Date 2020-02-21 23:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapperScans {

    MapperScan[] value();
}
