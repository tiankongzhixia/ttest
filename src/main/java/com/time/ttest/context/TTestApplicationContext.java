package com.time.ttest.context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-23 17:38
 */
@Singleton
public class TTestApplicationContext extends AbstractApplicationContext {

    private Injector injector;

    public void setInjector(Injector injector){
        this.injector = injector;
    }

    @Override
    public Injector getInjector() {
        return injector;
    }
}
