package com.time.ttest.util;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther guoweijie

 * @Date 2020-02-28 04:04
 */
public class ParamUtil {

    private static final Paranamer paranamer = new BytecodeReadingParanamer();

    /**
     * 将参数名称和参数值对应
     * @param method 方法
     * @param args 方法参数
     * @return 名称参数值对应
     */
    public static Map<String,Object> getParametersMap(Method method, final Object... args){
        final Map<String, Object> params = new HashMap<>();
        //获取方法所有的参数名称
        String[] parameterNames = paranamer.lookupParameterNames(method);
        for (int i = 0; i < Math.min(parameterNames.length, args.length); i++) {
            params.put(parameterNames[i], args[i]);
            params.put(Integer.toString(i), args[i]);
        }
        return params;
    }

    /**
     *  {*} 根据方法名获取参数
     * @param template 方法名称
     * @param params 方法参数
     * @return 参数，找不到则返回方法名
     */
    public static Object processNameTemplate(final String template, final Map<String, Object> params) {
        final Matcher matcher = Pattern.compile("\\{([^}]*)}").matcher(template);
        while (matcher.find()) {
            final String pattern = matcher.group(1);
            return params.getOrDefault(pattern,template);
        }
        return template;
    }
}
