package com.time.ttest;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用Guice管理Tesng内的实例
 */
@Slf4j
@Singleton
public class TTestInjectorFactory implements org.testng.IInjectorFactory {

    private Injector injector;

    public TTestInjectorFactory() {
        TTestApplication tTestApplication = new TTestApplication();
        injector = tTestApplication.getInjector();
    }

    @Override
    public Injector getInjector(Stage stage, Module... modules) {
        return injector;
    }
}
