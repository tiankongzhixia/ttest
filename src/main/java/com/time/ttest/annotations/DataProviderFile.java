package com.time.ttest.annotations;

import com.google.gson.JsonObject;
import com.time.ttest.GsonBuilder;
import com.time.ttest.TTestGsonBuilder;

import java.lang.annotation.*;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 19:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface DataProviderFile {
    /**
     * 路径
     * @return
     */
    String value();

    /**
     * 需要转换的类型
     * @return
     */
    Class transform() default JsonObject.class;

    Class<? extends GsonBuilder> gson() default TTestGsonBuilder.class;
}
