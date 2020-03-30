package com.time.ttest.file;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.time.ttest.util.ParameterizedTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.collections.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * json文件
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:23
 */
@Slf4j
public class JsonFile<T> extends AbsFile<T> {

    private Gson gson;

    public JsonFile(String fileName) {
        super(fileName);
    }


    public JsonFile(String fileName,Gson gson) {
        super(fileName);
        this.gson = gson;
    }

    @Override
    public T transformation(Class target){
        T targetObject = getTargetObject();
        if (null == targetObject){
            setTargetObject((T) transformationJson(getInputStream(),target));
        }
        return getTargetObject();
    }



    /**
     * 将json文件进行转换
     * @param inputStream json文件流
     * @param target 转换的目标对象
     * @return
     * @throws IOException
     */
    private List<T> transformationJson(InputStream inputStream, Class<?> target) {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        try {
            List<T> targetList = Lists.newArrayList();
            JsonToken peek = jsonReader.peek();
            Gson gson = getGson();
            if (peek == JsonToken.BEGIN_OBJECT){
                Type type = TypeToken.get(target).getType();
                targetList.add(gson.fromJson(jsonReader,type));
            }else if (peek == JsonToken.BEGIN_ARRAY){
                //类型转换
                targetList = gson.fromJson(jsonReader, ParameterizedTypeUtil.generateList(target));
            }else {
                throw new JsonIOException("json 文件仅支持 jsonObject 和 jsonArray");
            }
            jsonReader.close();
            return targetList;
        } catch (IOException e) {
            log.error("json文件读取异常：error:{}", e);
            throw new JsonIOException(e);
        }
    }

    /**
     * 设置自定义gson解析器gson
     * @param gson
     */
    public void setGson(Gson gson){
        this.gson = gson;
    }

    /**
     * 获取 gson，如果没有则赋值
     * @return
     */
    public Gson getGson() {
        if (null == this.gson) this.gson = new Gson();
        return this.gson;
    }
}
