package com.time.ttest.util;

import io.qameta.allure.*;
import io.qameta.allure.util.NamingUtils;
import org.testng.IAttributes;
import org.testng.ITestResult;
import org.testng.collections.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取Tesng 运行实例的附加属性
 * @Auther guoweijie
 * @Date 2020-02-26 22:01
 */
public class AttributesUtil {

    public static void set(IAttributes attributes,String key,Object value){
        attributes.setAttribute(key,value);
    }

    public static Map<String,Object> getAll(IAttributes attributes){
        Map<String,Object> map = new HashMap<>();
        attributes.getAttributeNames().forEach(k ->{
            map.put(k,attributes.getAttribute(k));
        });
        return map;
    }

    public static void setAllureAnnotation(IAttributes attributes, Method method) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (attributes instanceof ITestResult){
            ITestResult result = (ITestResult) attributes;
            setAllureAnnotation(attributes, method.getAnnotations(),ParamUtil.getParametersMap(method,result.getParameters()));
        }else {
            setAllureAnnotation(attributes, method.getAnnotations());
        }

    }

    public static void setAllureAnnotation(IAttributes attributes, Annotation[] annotations) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        setAllureAnnotation(attributes, annotations,null);
    }

    /**
     * 设置Allure注解内容
     * 兼容 {*(方法名称)}写法 替换花括号内的值
     * @param attributes
     * @param annotations
     * @param parameters
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void setAllureAnnotation(IAttributes attributes, Annotation[] annotations,Map<String,Object> parameters) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Annotation annotation:annotations){
            if (annotation instanceof Epic){
                setAllureAnnotation(attributes,
                        Epic.class,
                        NamingUtils.processNameTemplate(((Epic) annotation).value(),parameters));
            }
            if (annotation instanceof Epics){
                setAllureAnnotations(attributes,Epic.class,((Epics) annotation).value(),parameters);
            }
            if (annotation instanceof Feature){
                setAllureAnnotation(attributes,Feature.class,NamingUtils.processNameTemplate(((Feature) annotation).value(),parameters));
            }
            if (annotation instanceof Features){
                setAllureAnnotations(attributes,Feature.class,((Features) annotation).value(), parameters);
            }
            if (annotation instanceof Issue){
                setAllureAnnotation(attributes,Issue.class,NamingUtils.processNameTemplate(((Issue) annotation).value(),parameters));
            }
            if (annotation instanceof Issues){
                setAllureAnnotations(attributes,Issue.class,((Issues) annotation).value(), parameters);
            }
            if (annotation instanceof LabelAnnotation){
                setAllureAnnotation(attributes,LabelAnnotation.class,((LabelAnnotation) annotation).value());
            }
            if (annotation instanceof LabelAnnotations){
                setAllureAnnotations(attributes,LabelAnnotation.class,((LabelAnnotations) annotation).value(), null);
            }
            if (annotation instanceof Link){
                setAllureAnnotation(attributes,Link.class,NamingUtils.processNameTemplate(((Link) annotation).value(),parameters));
            }
            if (annotation instanceof Links){
                setAllureAnnotations(attributes,Link.class,((Links) annotation).value(), parameters);
            }
            if (annotation instanceof Step){
                setAllureAnnotation(attributes,Step.class, NamingUtils.processNameTemplate(((Step) annotation).value(),parameters));
            }
            if (annotation instanceof Story){
                setAllureAnnotation(attributes,Story.class,NamingUtils.processNameTemplate(((Story) annotation).value(),parameters));
            }
            if (annotation instanceof TmsLink){
                setAllureAnnotation(attributes,TmsLink.class,NamingUtils.processNameTemplate(((TmsLink) annotation).value(),parameters));
            }
            if (annotation instanceof TmsLinks){
                setAllureAnnotations(attributes,TmsLink.class,((TmsLinks) annotation).value(), parameters);
            }
            if (annotation instanceof Severity){
                setAllureAnnotation(attributes,Severity.class,((Severity) annotation).value());
            }
            if (annotation instanceof Owner){
                setAllureAnnotation(attributes,Owner.class,NamingUtils.processNameTemplate(((Owner) annotation).value(),parameters));
            }
        }
    }


    private static void setAllureAnnotation(IAttributes attributes,Class clazz,Object value){
        String className = clazz.getSimpleName().toLowerCase();
        Object attribute = attributes.getAttribute(className);
        if (null == attribute){
            attributes.setAttribute(className, Lists.newArrayList(value));
        }else {
            List<Object> objects = (List<Object>) attributes.getAttribute(className);
            objects.add(value);
        }
    }

    private static void setAllureAnnotations(IAttributes attributes, Class clazz, Object[] annotations, Map<String, Object> parameters) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Object o:annotations){
            Method method = clazz.getDeclaredMethod("value");
            Object value = method.invoke(o, (Object[]) null);
            setAllureAnnotation(attributes,clazz,parameters==null?value:NamingUtils.processNameTemplate(String.valueOf(value),parameters));
        }
    }


}
