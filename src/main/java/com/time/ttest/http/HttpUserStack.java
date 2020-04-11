package com.time.ttest.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 19:04
 */
@Singleton
public class HttpUserStack {
    @Inject
    private HttpManagerFactory httpManagerFactory;

    private final ConcurrentHashMap<String,Stack<String>> threadUserStack = new ConcurrentHashMap<String,Stack<String>>();

    private final Stack<String> tempStack = new Stack<>();

    public void pushUser(String user){
        threadUserStack.compute(Thread.currentThread().getName(),(key,value)->{
            if (null == value){
                value = new Stack<>();
            }
            value.push(user);
            return value;
        });
    }

    public String peekUser(){
        try {
            return threadUserStack.getOrDefault(Thread.currentThread().getName(),tempStack).peek();
        }catch (EmptyStackException e){
            return httpManagerFactory.getHttpManager().getDefaultUserName();
        }
    }

    /**
     * 从当前线程取出需要
     * @return
     */
    public String popUser(){
        try {
            return threadUserStack.getOrDefault(Thread.currentThread().getName(),tempStack).pop();
        }catch (EmptyStackException e){
            return httpManagerFactory.getHttpManager().getDefaultUserName();
        }
    }
}
