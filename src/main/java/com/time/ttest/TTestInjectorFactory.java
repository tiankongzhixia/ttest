package com.time.ttest;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TTestInjectorFactory implements org.testng.IInjectorFactory {

    private Injector injector;

    public TTestInjectorFactory() {
        TTestApplication tTestApplication = new TTestApplication();
        injector = tTestApplication.getInjector();
    }

    @Override
    public Injector getInjector(Stage stage, Module... modules) {
        for (Module module: modules) {
            if (module instanceof TTestModule){
                return injector;
            }else {
                return injector.createChildInjector(module);
            }
        }
        return injector;
    }
}
