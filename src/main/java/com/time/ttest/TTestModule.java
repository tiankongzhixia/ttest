package com.time.ttest;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.time.ttest.annotations.Module;
import com.time.ttest.http.HttpModule;
import com.time.ttest.module.BaseModule;
import com.time.ttest.module.TTestMyBatisModule;
import com.time.ttest.module.TTestServiceModule;
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
        install(HttpModule.getModule());
        install(TTestAopModule.getModule());
        Names.bindProperties(binder(), getApplication().getProperties());
        //mybatis Druid 多数据源配置
        TTestMyBatisModule.getModules(this.getApplication()).forEach(tTestMyBatisModule -> {
            install(new PrivateModule() {
                @Override
                protected void configure() {
                    install(tTestMyBatisModule);
                    Set<Class<? extends BaseMapper>> subTypesOf = ReflectionsUtil.getSubTypesOf(tTestMyBatisModule.getMapperScan().value(), BaseMapper.class);
                    subTypesOf.forEach(this::expose);
                }
            });
        });
        install(TTestServiceModule.getModule(this.getApplication()));
        //扫描用户包下 @Module 注解 并注入到Guice
        ReflectionsUtil.getTypesAnnotatedWith(getApplication().getProperties().getProperty("package"), Module.class).forEach(module->{
            try {
                install((com.google.inject.AbstractModule) module.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("{}.class无法实例化 error:{}",module,e.getMessage());
            }
        });
    }
}
