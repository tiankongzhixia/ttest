package com.time.ttest.module;

import com.time.ttest.TTestApplication;
import com.time.ttest.annotations.Module;
import com.time.ttest.util.ReflectionsUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 18:54
 */
@Slf4j
public class IModule extends BaseModule {

    static IModule getModule(TTestApplication application){
        IModule configurationModule = new IModule();
        configurationModule.setApplication(application);
        return configurationModule;
    }

    @Override
    public void configure(){
        ReflectionsUtil.getTypesAnnotatedWith(getApplication().getProperties().getProperty("package"), Module.class).forEach(module->{
            try {
                install((com.google.inject.AbstractModule) module.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("{}.class无法实例化 error:{}",module,e.getMessage());
            }
        });
    }
}
