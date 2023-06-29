package com.heaven.palace.moonpalace.common.constant.dictmap.factory;

import com.heaven.palace.moonpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.moonpalace.common.constant.factory.IConstantFactory;

import java.lang.reflect.Method;

/**
 * 字段的包装创建工厂
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object field, String methodName) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, field.getClass());
            Object result = method.invoke(me, field);
            return result;
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(field.toString()));
                return result;
            } catch (Exception e1) {
                throw new RuntimeException();
            }
        }
    }

}
