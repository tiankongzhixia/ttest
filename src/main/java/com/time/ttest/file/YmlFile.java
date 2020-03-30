package com.time.ttest.file;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Yml文件
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-22 23:25
 */
@Slf4j
public class YmlFile<T> extends AbsFile<T> {

    public YmlFile(String fileName) {
        super(fileName);
    }

    @Override
    public T transformation(Class target){
        T targetObject = getTargetObject();
        if (null == targetObject){
            setTargetObject((T) transformationYml(getInputStream(),target));
        }
        return getTargetObject();
    }

    /**
     * 转换内容为目标对象
     * @param inputStream 文件流
     * @param target 目标对象
     * @return
     * @throws IOException
     */
    private Object transformationYml(InputStream inputStream, Class<T> target) {
        Yaml yaml = new Yaml();
        try {
            T t = yaml.loadAs(inputStream, target);
            inputStream.close();
            return t;
        }catch (IOException e){
            log.error("yml文件读取异常：error:{}", e);
            throw new YAMLException(e);
        }
    }

}
