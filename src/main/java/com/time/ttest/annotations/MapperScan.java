package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * mapper扫描注解
 * 根据该注解不同的数据库注入不同的mapper
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(MapperScans.class)
public @interface MapperScan {

    /**
     * 包名
     */
    String value();

    /**
     * 数据库名
     */
    String datasource();
}
