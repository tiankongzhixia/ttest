package com.time.ttest.file;

import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.time.ttest.util.GsonUtil;
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

 * @Date 2020-03-18 23:23
 */
@Slf4j
public class JsonFile<T> extends AbsFile<T> {


    public JsonFile(String fileName) {
        super(fileName);
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
            if (peek == JsonToken.BEGIN_OBJECT){
                Type type = TypeToken.get(target).getType();
                targetList.add(GsonUtil.getGson().fromJson(jsonReader,type));
            }else if (peek == JsonToken.BEGIN_ARRAY){
                //类型转换
                targetList = GsonUtil.getGson().fromJson(jsonReader, ParameterizedTypeUtil.generateList(target));
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

}
