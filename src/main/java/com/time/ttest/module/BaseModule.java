package com.time.ttest.module;


import com.google.inject.AbstractModule;
import com.time.ttest.TTestApplication;

public abstract class BaseModule extends AbstractModule {

    private TTestApplication application;

    public void setApplication(TTestApplication application){
        this.application = application;
    }

    public TTestApplication getApplication(){
        return this.application;
    }
}
