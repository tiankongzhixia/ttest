package com.time.ttest.http;

import com.google.inject.Provides;
import com.time.ttest.annotations.UserName;
import com.time.ttest.http.impl.HttpTemplateImpl;
import com.time.ttest.module.BaseModule;
import kong.unirest.UnirestInstance;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HttpModule extends BaseModule {

    public static HttpModule getModule(){
        return new HttpModule();
    }

    public void configure(){
        bind(HttpTemplate.class).to(HttpTemplateImpl.class);
    }



    @Provides
    @UserName
    String getUserName(UserFactory  userFactory){
        return userFactory.getThreadUserManager().getUserName();
    }

    @Provides
    public UnirestInstance getUnirestInstance(
           @UserName String name,
            UnirestFactory unirestFactory){
        return unirestFactory.get(name);
    }
}
