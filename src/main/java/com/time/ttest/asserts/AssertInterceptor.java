package com.time.ttest.asserts;

import com.google.inject.Inject;
import com.time.ttest.annotations.Assert;
import com.time.ttest.annotations.Asserts;
import com.time.ttest.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.testng.collections.Lists;
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
    
    @Inject
    private  TAssert tAssert;

    /**
     * 拦截标有@Assert和@Asserts的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        List<TTestAssert> tTestAsserts = generateAsserts(methodInvocation,result);
        tTestAsserts.forEach(tTestAssert -> {
            tAssert.startAssert(tTestAssert);
        });
        return result;
    }

    /**
     * 组装所有的Assert内容
     * @param methodInvocation 方法
     * @param result 返回值
     * @return 所有的Assert
     */
    private List<TTestAssert> generateAsserts(MethodInvocation methodInvocation, Object result) {
        Map<String, Object> parametersMap = ParamUtil.getParametersMap(methodInvocation.getMethod(), methodInvocation.getArguments());
        List<TTestAssert> tTestAsserts = Lists.newArrayList();
        List<Assert> assertList = Lists.newArrayList();
        if (methodInvocation.getMethod().isAnnotationPresent(Assert.class)){
            assertList.add(methodInvocation.getMethod().getAnnotation(Assert.class));
        }
        if (methodInvocation.getMethod().isAnnotationPresent(Asserts.class)){
            assertList.addAll(Arrays.asList(methodInvocation.getMethod().getAnnotation(Asserts.class).value()));
        }
        assertList.forEach(anAssert -> {
            tTestAsserts.add(generateAssert(anAssert,result,parametersMap));
        });
        return tTestAsserts;
    }

    private TTestAssert generateAssert(Assert assertAnnotation,Object result,Map<String, Object> parametersMap){
        TTestAssert tTestAssert = new TTestAssert();
        tTestAssert.setActual(result);
        tTestAssert.setActualPath(assertAnnotation.path());
        tTestAssert.setExpect(ParamUtil.processNameTemplate(assertAnnotation.expect(),parametersMap));
        tTestAssert.setExpectPath(assertAnnotation.expectPath());
        tTestAssert.setAssertTypeEnum(assertAnnotation.assertType());
        tTestAssert.setErrorMsg(assertAnnotation.errorMsg());
        return tTestAssert;
    }
}
