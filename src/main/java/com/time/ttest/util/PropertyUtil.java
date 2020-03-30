package com.time.ttest.util;
import java.util.*;

public class PropertyUtil {

//    public static Properties loadFile(String fileName, Class<?> clazz) throws IOException {
//        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//        InputStream resourceAsStream = clazz.getResourceAsStream("/"+fileName);
//        if (suffix.equals("yml")){
//            return new TTestProperties(fileName);
//        }
//        return initYml(resourceAsStream);
//    }
//
//    private static Properties initYml(InputStream inputStream) throws Throwable {
//        Yaml yaml = new Yaml();
//        Iterator<Object> result = yaml.loadAll(inputStream).iterator();
//        Properties properties = new TTestProperties();
//        while(result.hasNext()) {
//            Map map = (Map) result.next();
//            iteratorYml(properties,map,null);
//        }
//        return properties;
//    }
//
//    private static void iteratorYml(Properties properties, Map map,String key) {
//        Iterator iterator = map.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry entry = (Map.Entry) iterator.next();
//            Object mapKey = entry.getKey();
//            Object mapValue = entry.getValue();
//            if(mapValue instanceof LinkedHashMap){
//                if (StringUtils.isEmpty(key)){
//                    iteratorYml(properties,(Map)mapValue,mapKey.toString());
//                }else {
//                    iteratorYml(properties,(Map)mapValue,key +"."+mapKey.toString());
//                }
//            }else if (mapValue instanceof String || mapValue instanceof Boolean || mapValue instanceof Integer || mapValue instanceof Long){
//                if (StringUtils.isEmpty(key)){
//                    properties.put(mapKey,String.valueOf(mapValue));
//                }else {
//                    properties.put(key+"."+mapKey,String.valueOf(mapValue));
//                }
//            }
//        }
//    }


    public static Properties replacePropertyPrefix(Properties properties, String prefix, String addPrefix){
        Properties prefixProperties = new Properties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()){
            String key = String.valueOf(keys.nextElement());
            if (key.indexOf(prefix) == 0){
                prefixProperties.put(addPrefix+key.replace(prefix,""),properties.get(key));
            }
        }
        return prefixProperties;
    }
}
