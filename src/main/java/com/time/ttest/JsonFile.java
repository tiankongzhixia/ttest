package com.time.ttest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.testng.collections.Lists;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:23
 */
public class JsonFile extends AbsFile {

    private Gson gson;

    public JsonFile(String fileName) {
        super(fileName);
    }

    @Override
    public Gson getGson() {
        if (null == this.gson){
            this.gson = new Gson();
        }
        return this.gson;
    }

    public void setGson(Gson gson){
        this.gson = gson;
    }

    public JsonFile(String fileName,Gson gson) {
        super(fileName);
        this.gson = gson;
    }

    @Override
    public <T> List<T> transformation(Class target) throws IOException {
        InputStream inputStream = super.getInputStream();
        return jsonTransformation(inputStream,target);
    }

    @Override
    public <T> Object[][] dataProviderTransfer(Class target) throws IOException {
        List<Object> targetList = transformation(target);
        Object[][] dataProvider = new Object[targetList.size()][1];
        for (int i =0;i<targetList.size();i++){
            dataProvider[i][0] = targetList.get(i);
        }
        return dataProvider;
    }

    /**
     * 将json文件进行转换
     * @param inputStream json文件流
     * @param target 转换的目标对象
     * @return
     * @throws IOException
     */
    private <T> List<T> jsonTransformation(InputStream inputStream, Class<?> target) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        List<Object> targetList = Lists.newArrayList();
        JsonToken peek = jsonReader.peek();
        Gson gson = getGson();
        if (peek == JsonToken.BEGIN_OBJECT){
            Type type = TypeToken.get(target).getType();
            targetList.add(gson.fromJson(jsonReader,type));
        }else if (peek == JsonToken.BEGIN_ARRAY){
            Type parameterizedType =ParameterizedTypeImpl.make(List.class, new Type[]{TypeToken.get(target).getType()}, null);
            targetList = gson.fromJson(jsonReader,parameterizedType);
        }
        return (List<T>) targetList;
    }
}
