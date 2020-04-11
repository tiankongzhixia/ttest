package com.time.ttest.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import kong.unirest.Unirest;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 19:17
 */
@Singleton
public class HttpService<T> {

    @Inject
    private HttpUserStack httpUserStack;

    @Inject
    private HttpManagerFactory httpManagerFactory;

    @SuppressWarnings("unchecked")
    public T start(){
        String currUser = httpUserStack.peekUser();
        if (null == currUser)
            return (T)  httpManagerFactory.getHttpManager().getDefaultUser();
        return (T) httpManagerFactory
                .getHttpManager()
                .get(currUser);
    }
}
