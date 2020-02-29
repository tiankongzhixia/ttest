package com.time.ttest.module;

import com.time.ttest.TTestApplication;
import com.time.ttest.annotations.Service;
import com.time.ttest.util.ReflectionsUtil;

import java.util.Set;


public class TTestServiceModule extends BaseModule {

    public static TTestServiceModule getModule(TTestApplication application){
        TTestServiceModule serviceModule = new TTestServiceModule();
        serviceModule.setApplication(application);
        return serviceModule;
    }

    @Override
    public void configure(){
        Set<Class<?>> serviceAnnotatedWiths = ReflectionsUtil.getTypesAnnotatedWith(getApplication().getProperties().getProperty("package"), Service.class);
        serviceAnnotatedWiths.forEach(serviceAnnotatedWith ->{
            bind(serviceAnnotatedWith.getInterfaces()[0]).to((Class) serviceAnnotatedWith);
        });
    }
}
