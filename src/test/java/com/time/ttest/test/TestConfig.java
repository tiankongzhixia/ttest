package com.time.ttest.test;

import com.google.gson.*;
import com.time.ttest.annotations.*;
import com.time.ttest.test.ast.TTestAssertDTO;
import kong.unirest.Config;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

/**
 * @Auther guoweijie

 * @Date 2020-04-10 23:02
 */
@TTestConfiguration
@EnableHttpManager
//@MapperScans({
//        @MapperScan(value = "com.time.ttest.test.mapper.magic",datasource = "magic"),
//        @MapperScan(value = "com.time.ttest.test.mapper.ttest",datasource = "ttest")
//})
public class TestConfig {

    @HttpUserBean(value = "default",isMain = true)
    public Config defaultUser(){
        Config config = new Config();
        config.setDefaultHeader("Token","default-user");
        return config;
    }

    @HttpUserBean(value = "newUser")
    public Config newUser(){
        Config config = new Config();
        config.setDefaultHeader("Token","new-user");
        return config;
    }

    @GsonBean
    public Gson gson(){
        return new GsonBuilder()
                .registerTypeAdapter(java.util.Date.class,new DateDeserializer())
                .setDateFormat(DateFormat.LONG)
                .create();
    }

    @DataProvider(name = "data1")
    @DataProviderFile(value = "test_json.json",transform = TTestAssertDTO.class)
    public Object[][] data1(){ return null; }

    @DataProvider(name = "data2")
    @DataProviderFile(value = "test_yml.yml",transform = TTestAssertDTO.class)
    public Object[][] data2(){ return null; }

    /**
     * Long转日期
     */
    public class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
        }
    }
}
