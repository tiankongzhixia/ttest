package com.time.ttest.context;

import com.time.ttest.listener.ApplicationListener;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

    @Getter
    private Set<ApplicationListener<?>> listeners = new HashSet<>();


    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }
}
