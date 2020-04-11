package com.time.ttest.module;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.time.ttest.TTestApplication;
import com.time.ttest.annotations.Module;
import com.time.ttest.annotations.TTestConfiguration;
import com.time.ttest.util.ReflectionsUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class TTestModule extends BaseModule {

    public static TTestModule getModule(TTestApplication application){
        TTestModule testModule = new TTestModule();
        testModule.setApplication(application);
        return testModule;
    }

    @Override
    public void configure(){
        install(TTestAopModule.getModule());
        Names.bindProperties(binder(), getApplication().getProperties());
        install(TTestConfigurationModule.getModule(getApplication()));
        install(TTestServiceModule.getModule(getApplication()));
        //扫描用户包下 @Module 注解 并注入到Guice
        install(IModule.getModule(getApplication()));
    }
}
