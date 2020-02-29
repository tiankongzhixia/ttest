package com.time.ttest.http;

import com.time.ttest.context.TTestApplicationContext;
import kong.unirest.Config;
import kong.unirest.UnirestInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Slf4j
public class UnirestFactory{
    @Inject
    private HttpInterceptor httpInterceptor;
    @Inject
    private TTestApplicationContext context;
    private ConcurrentHashMap<String, UnirestInstance> unirestInstanceConcurrentHashMap = new ConcurrentHashMap<String, UnirestInstance>();
    private UnirestInstance defaultUnirestInstance;

    public UnirestInstance get(String key){
        if (StringUtils.isEmpty(key)){
           return getDefaultUnirestInstance();
        }
        if (unirestInstanceConcurrentHashMap.containsKey(key)){
            return unirestInstanceConcurrentHashMap.get(key);
        }
        throw new NoSuchElementException("Can't find ["+key+"] unirest , available "+ unirestInstanceConcurrentHashMap.keys());
    }

    private UnirestInstance getDefaultUnirestInstance() {
        if (null == defaultUnirestInstance){
            defaultUnirestInstance  = new UnirestInstance(preConfig(null));
        }
        return defaultUnirestInstance;
    }

    public void put(String key,UnirestInstance unirestInstance){
        preConfig(unirestInstance.config());
        unirestInstanceConcurrentHashMap.put(key,unirestInstance);
    }

    public void putDefault(UnirestInstance unirestInstance){
        preConfig(unirestInstance.config());
        defaultUnirestInstance = unirestInstance;
    }

    /**
     * 给所有的unirest配置ttest的config
     * @param config
     * @return
     */
    private Config preConfig(Config config){
        if (null == config){
            config = new Config();
        }
        config.interceptor(httpInterceptor)
                .instrumentWith(new HttpUniMetric(context));
        return config;
    }
}
