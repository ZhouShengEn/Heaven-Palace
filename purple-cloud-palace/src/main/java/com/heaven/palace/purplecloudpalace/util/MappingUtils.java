package com.heaven.palace.purplecloudpalace.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

import java.util.List;


/**
 * @author :zhoushengen
 * @date : 2022/9/7
 * orika对象映射
 */
public final class MappingUtils {

    private static MapperFacade mapperFacade;

    private static MapperFacade getMapperFacade() {
        if (null == mapperFacade) {
            mapperFacade = SpringContextUtils.getBean(MapperFactory.class).getMapperFacade();
        }
        return mapperFacade;

    }


    /**
     * bean对象转换
     * @param sourceObject
     * @param targetClass
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> K beanConvert(T sourceObject, Class<K> targetClass){
        return getMapperFacade().map(sourceObject, targetClass);
    }

    /**
     * bean列表对象转换
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> beanListConvert(List<T> sourceList, Class<K> targetClass) {
        return getMapperFacade().mapAsList(sourceList, targetClass);
    }


    /**
     * bean对象覆盖
     * @param sourceObject
     * @param targetObject
     * @param <T>
     * @param <K>
     */
    public static <T, K> void beanConvert(T sourceObject, K targetObject){
        getMapperFacade().map(sourceObject, targetObject);
    }


}
