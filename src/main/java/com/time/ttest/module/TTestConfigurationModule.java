package com.time.ttest.module;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.inject.MembersInjector;
import com.google.inject.PrivateModule;
import com.time.ttest.TTestApplication;
import com.time.ttest.TTestException;
import com.time.ttest.annotations.*;
import com.time.ttest.http.HttpManager;
import com.time.ttest.http.HttpManagerFactory;
import com.time.ttest.util.ReflectionsUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 18:14
 */
public class TTestConfigurationModule extends BaseModule{
    private List<TTestMyBatisModule> myBatisModuleList = new ArrayList<>();

    public static TTestConfigurationModule getModule(TTestApplication application){
        TTestConfigurationModule configurationModule = new TTestConfigurationModule();
        configurationModule.setApplication(application);
        return configurationModule;
    }

    @Override
    public void configure(){
        for (Class<?> classs : getApplication().getConfigurations()) {
            configuration(classs);
        }
        myBatisModuleList.forEach(tTestMyBatisModule -> {
            install(new PrivateModule() {
                @Override
                protected void configure() {
                    install(tTestMyBatisModule);
                    Set<Class<? extends BaseMapper>> subTypesOf = ReflectionsUtil.getSubTypesOf(tTestMyBatisModule.getMapperPackage(), BaseMapper.class);
                    subTypesOf.forEach(this::expose);
                }
            });
        });
    }
    private void configuration(Class clazz) {
        //注册mybatis
        if (clazz.isAnnotationPresent(MapperScan.class) || clazz.isAnnotationPresent(MapperScans.class)){
            myBatisModuleList.addAll(TTestMyBatisModule.getModules(getApplication(),clazz));
        }
    }

}
