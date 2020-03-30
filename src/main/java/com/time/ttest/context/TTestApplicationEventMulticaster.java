package com.time.ttest.context;

import com.time.ttest.event.ApplicationEvent;
import com.time.ttest.listener.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 广播
 */
public class TTestApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    @Override
    public void multicastEvent(ApplicationEvent event) {
        Iterator<ApplicationListener<?>> iterator = getApplicationListeners(event).iterator();
        while (iterator.hasNext()){
            ApplicationListener<?> next = iterator.next();
            invokeListener(next,event);
        }
    }

    /**
     * 获取所有该接收消息的监听器
     * @param event
     * @return
     */
    private Set<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
        Set<ApplicationListener<?>> listeners = new LinkedHashSet<>();
        this.getListeners().forEach(listener -> {
            //判断 listener 实现的 listener<event> event类型是否一致 或者是子类
            ParameterizedType parameterizedType = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Class clazz = (Class) actualTypeArguments[0];
            if (clazz == event.getClass() || clazz.isAssignableFrom(event.getClass())){
                listeners.add(listener);
            }
        });
        return listeners;
    }


    private void invokeListener(ApplicationListener applicationListener,ApplicationEvent applicationEvent){
        applicationListener.onApplicationEvent(applicationEvent);
    }
}
