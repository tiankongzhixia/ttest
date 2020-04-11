package com.time.ttest.test.user;

import com.google.inject.Inject;
import com.time.ttest.annotations.HttpUser;
import com.time.ttest.test.api.BaiduApi;
import kong.unirest.Headers;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 23:08
 */
@Guice
@Slf4j
public class UserAnnotationTest {

    @Inject
    private BaiduApi baiduApi;

    private void assertDefaultUserTest(){
        Headers headers = baiduApi.getHomePageRequestHeaders();
        String token = headers.getFirst("Token");
        Assert.assertNotNull(token);
        Assert.assertEquals(token,"default-user");

    }

    private void assertParamUser(){
        Headers newUserHeaders = baiduApi.getHomePageRequestHeaders("newUser");
        String token1 = newUserHeaders.getFirst("Token");
        Assert.assertNotNull(token1);
        Assert.assertEquals(token1,"new-user");

        Headers defaultHeaders = baiduApi.getHomePageRequestHeaders("default");
        String token = defaultHeaders.getFirst("Token");
        Assert.assertNotNull(token);
        Assert.assertEquals(token,"default-user");


    }

    @HttpUser("newUser")
    protected void assertNewUserTest(){
        Headers headers = baiduApi.getHomePageRequestHeaders();
        String token = headers.getFirst("Token");
        Assert.assertNotNull(token);
        Assert.assertEquals(token,"new-user");
    }

    @Test
    public void defaultUserTest(){
        assertDefaultUserTest();

    }

    @Test
    public void newUserTest(){
        assertNewUserTest();
    }

    @Test
    public void nestingUserTest(){
        assertDefaultUserTest();
        assertNewUserTest();
        assertDefaultUserTest();
        assertNewUserTest();
    }

    @Test(threadPoolSize = 5,invocationCount = 10)
    public void concurrentUserTest(){
        assertDefaultUserTest();
        assertNewUserTest();
        assertParamUser();
    }
}
