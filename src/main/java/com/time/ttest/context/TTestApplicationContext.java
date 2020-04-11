package com.time.ttest.context;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.Data;

import java.util.Properties;

/**
 * @Auther guoweijie

 * @Date 2020-02-23 17:38
 */
@Singleton
@Data
public class TTestApplicationContext extends AbstractApplicationContext {

    private Injector injector;

    private Properties properties;


}
