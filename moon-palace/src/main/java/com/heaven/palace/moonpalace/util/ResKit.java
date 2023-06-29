package com.heaven.palace.moonpalace.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * 资源文件相关的操作类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class ResKit {

    /**
     * @Description 批量获取ClassPath下的资源文件
     * @author ZhouShengEn
     */
    public static Resource[] getClassPathResources(String pattern) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(pattern);
        } catch (IOException e) {
            throw new RuntimeException("加载resource文件时,找不到文件,所找文件为：" + pattern);
        }
    }

    /**
     * @Description 批量获取ClassPath下的资源文件
     * @author ZhouShengEn
     */
    public static String getClassPathFile(String file) {
        //return ResKit.class.getClassLoader().getResource(file).getPath();
        return Thread.currentThread().getContextClassLoader().getResource(file).getPath();
    }
}
