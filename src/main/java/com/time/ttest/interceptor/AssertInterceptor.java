package com.time.ttest.interceptor;

import com.google.gson.Gson;
import com.jayway.jsonassert.JsonAssert;
import com.time.ttest.annotations.Assert;
import com.time.ttest.annotations.Asserts;
import com.time.ttest.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.collections.Lists;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 断言 拦截器
 * @Auther guoweijie

 * @Date 2020-02-25 22:52
 */
@Slf4j
public class AssertInterceptor implements MethodInterceptor {

    private final Gson gson = new Gson();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        if (null == result){
            log.error("method {} return to null, unable to assert",methodInvocation.getMethod().getName());
            return result;
        }
        Map<String, Object> parametersMap = ParamUtil.getParametersMap(methodInvocation.getMethod(), methodInvocation.getArguments());
        List<TTestAssert> assertParameters = getAssertParameter(methodInvocation.getMethod(),parametersMap);
        if (assertParameters.size() == 0){
            log.error("method {} @Assert content is empty,unable to assert",methodInvocation.getMethod().getName());
            return result;
        }
        asserts(assertParameters,result);
        return result;
    }

    private void asserts(List<TTestAssert> assertParameters, Object result) {
        assertParameters.forEach(tTestAssert -> {
            if (tTestAssert.getPath().equals("$")){
                Object expected = tTestAssert.getExpected();
                try {
                    //判断是否是包装类
                    if (((Class)tTestAssert.getTransform().getField("TYPE").get(null)).isPrimitive()){
                        Method valueOf = tTestAssert.getTransform().getDeclaredMethod("valueOf", String.class);
                        expected = valueOf.invoke(valueOf, expected);
                    }
                    org.testng.Assert.assertEquals(result,expected);
                } catch (Exception ignored) {
                    org.testng.Assert.assertEquals( result,expected,"Type conversion failure recommended to use the base type wrapper");
                }

            }else {
                String resultString = gson.toJson(result);
                JsonAssert.with(resultString).assertEquals(tTestAssert.getPath(),
                        gson.fromJson(gson.toJson(tTestAssert.getExpected()),tTestAssert.getTransform()));
            }
        });
    }

    /**
     * 获取@Assert注解的参数
     * @param method 方法
     * @param parametersMap 方法值
     * @return
     */
    private List<TTestAssert> getAssertParameter(Method method, Map<String, Object> parametersMap) {
        List<TTestAssert> parameterList = Lists.newArrayList();
        List<Assert> assertList = Lists.newArrayList();
        if (method.isAnnotationPresent(Assert.class)){
            assertList.add(method.getAnnotation(Assert.class));
        }
        if (method.isAnnotationPresent(Asserts.class)){
            assertList.addAll(Arrays.asList(method.getAnnotation(Asserts.class).value()));
        }
        assertList.forEach(anAssert -> {
            parameterList.add(new TTestAssert(anAssert.path(),
                    ParamUtil.processNameTemplate(anAssert.expect(),parametersMap),
                    anAssert.transform()
            ));
        });
        return parameterList;
    }
}
