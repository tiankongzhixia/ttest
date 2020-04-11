package com.time.ttest;

import com.google.inject.*;
import com.time.ttest.annotations.TTestConfiguration;
import com.time.ttest.context.AbstractApplicationContext;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.listener.ApplicationListener;
import com.time.ttest.listener.EventPublishingRunListener;
import com.time.ttest.module.TTestConfigurationModule;
import com.time.ttest.module.TTestModule;
import com.time.ttest.proxy.TTestNGProxy;
import com.time.ttest.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.IInjectorFactory;
import org.testng.TestNG;
import org.testng.collections.Lists;

import java.util.*;

@Slf4j
@Singleton
public class TTestApplication {

    @Getter
    private Injector injector;
    @Getter
    private List<ApplicationListener<?>> listeners = Lists.newArrayList();
    @Getter
    private Properties properties = new TTestProperties();
    @Getter
    private Set<Class<?>> configurations;

    public TTestApplication(){
        init();
    }

    private void init(){
        printBanner();
        scanConfig();
        injector = Guice.createInjector(Stage.PRODUCTION, TTestModule.getModule(this));
        initListeners();
    }

    private void scanConfig() {
        configurations = ReflectionsUtil.getTypesAnnotatedWith(properties.getProperty("package"), TTestConfiguration.class);
    }

    private void initListeners(){
        //添加监听器
        this.setListeners(ReflectionsUtil.getSubTypesOf(this.getClass().getPackage().getName(), ApplicationListener.class));
        this.setListeners(ReflectionsUtil.getSubTypesOf(properties.getProperty("package"), ApplicationListener.class));
        EventPublishingRunListener listener = new EventPublishingRunListener(this);
        listener.starting();
        TTestApplicationContext context = createApplicationContext();
        preContext(context,listeners);
        listener.running(context);
    }

    /**
     * 添加监听器
     * @param applicationListeners 监听器列表
     */
    private void setListeners(Set<Class<? extends ApplicationListener>> applicationListeners) {
        applicationListeners.forEach(listener->{
            listeners.add(injector.getInstance(listener));
        });
    }

    private void printBanner() {
        TTestBanner tTestBanner = new TTestBanner();
        tTestBanner.printBanner(System.out);
    }


    private void preContext(AbstractApplicationContext context, List<ApplicationListener<?>> listeners) {
        listeners.forEach(context::addApplicationListener);
    }

    private TTestApplicationContext createApplicationContext() {
        TTestApplicationContext context = injector.getInstance(TTestApplicationContext.class);
        context.setInjector(injector);
        context.setProperties(properties);
        return context;
    }


    /**
     * 代码启动方法
     * @param clazz 启动类
     * @param args 启动参数
     */
    public static void run(Class clazz,String[] args) {
        TestNG testNG = generateTestNg(clazz, args);
        System.exit(testNG.getStatus());
    }

    public static TestNG generateTestNg(Class clazz,String[] args) {
        Properties properties = new TTestProperties(clazz);
        TTestNGProxy testNGProxy = new TTestNGProxy(new TestNG(),properties,args);
        testNGProxy.run();
        return testNGProxy.getTestNG();
    }
}
