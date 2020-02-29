package com.time.ttest.report;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationEndEvent;
import com.time.ttest.http.UnirestFactory;
import com.time.ttest.listener.ApplicationListener;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;

/**
 * @Auther guoweijie

 * @Date 2020-02-27 05:35
 */
@Slf4j
public class TTestReportHookListener implements ApplicationListener<ApplicationEndEvent> {

    private final String hook;
    private final String hookUser;
    private final TTestApplicationContext context;

    @Inject
    public TTestReportHookListener(@Named("ttest.report.hook") String hook,
                                   @Named("ttest.report.hookUser") String hookUser,
                                   TTestApplicationContext context) {
        this.hook = hook;
        this.hookUser = hookUser;
        this.context = context;
    }


    @Override
    public void onApplicationEvent(ApplicationEndEvent event) {
        if (event.getSource() instanceof TTestReport && StringUtils.isNotEmpty(hook)){
            log.info(new Gson().toJson(event.getSource()));
            UnirestFactory unirestFactory = context.getInjector().getInstance(UnirestFactory.class);
            UnirestInstance unirestInstance = unirestFactory.get(hookUser);
            HttpResponse response = unirestInstance.post(hook).body(event.getSource()).asEmpty();
            log.info("post hook status {}",response.getStatus());
            response.ifFailure(httpResponse -> {
                log.info("post hook error {}",response.getBody());
            });
        }
    }
}
