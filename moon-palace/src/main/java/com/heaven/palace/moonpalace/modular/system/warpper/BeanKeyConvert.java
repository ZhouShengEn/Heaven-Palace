package com.heaven.palace.moonpalace.modular.system.warpper;

import com.heaven.palace.moonpalace.common.constant.factory.ConstantFactory;
import org.apache.ibatis.reflection.Reflector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础名称转换类 Created by ZhouShengEn on 2017/8/16.
 */
public class BeanKeyConvert {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanKeyConvert.class);

    private static Map<Class, Reflector> clazzMap = new HashMap<Class, Reflector>();

    /**
     * 系统用户名称转换类
     *
     * @param keys 值属性
     */
    public static void systemUserNameConvertOver(Object obj, String... keys) {
        try {
            if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    //迭代list,最终要走else
                    systemUserNameConvertOver(list.get(i), keys);
                }
            } else {
                //传进去一个空的map,是不是应该初始化一下
                doOver(obj, keys);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void systemUserNameConvert(List obj) {
        systemUserNameConvert(obj, "crtUserId", "crtUserName", "mdfUserId", "mdfUserName");
    }

    /**
     * 系统用户名称转换类
     *
     * @param keys 值属性,覆盖的属性,值属性,覆盖的属性 依次
     */
    public static void systemUserNameConvert(Object obj, String... keys) {
        try {
            if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    systemUserNameConvert(list.get(i), keys);
                }
            } else {
                doIt(obj, keys);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    //覆盖
    private static void doOver(Object obj, String[] keys) throws Exception {
        if (obj instanceof Map) {
            //不是map是实体类
            Map map = (Map) obj;
            for (int i = 0; i < keys.length; i++) {
                if (map.get(keys[i]) != null) {
                    map.put(keys[i], ConstantFactory.me().getUserNameById(Integer.parseInt(map.get(keys[i]).toString())));
                }
            }
        } else {
            //第一遍肯定是null啊,这是dao层用的包
            Reflector reflector = clazzMap.get(obj.getClass());
            if (reflector == null) {
                reflector = new Reflector(obj.getClass());
                clazzMap.put(obj.getClass(), reflector);
            }
            //好了现在有内容了:   反射 作为值,class 作为键
            for (int i = 0; i < keys.length; i++) {
                Object val = reflector.getGetInvoker(keys[i]).invoke(obj, null);
                if (val != null)
                    reflector.getSetInvoker(keys[i]).invoke(obj, new Object[]{ConstantFactory.me().getUserNameById(Integer.parseInt(val.toString()))});
            }
        }
    }

    //新增
    private static void doIt(Object obj, String[] keys) throws Exception {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            for (int i = 0; i < keys.length; i += 2) {
                if (map.get(keys[i]) != null) {
                    map.put(keys[i + 1], ConstantFactory.me().getUserNameById(Integer.parseInt(map.get(keys[i]).toString())));
                }
            }
        } else {
            Reflector reflector = clazzMap.get(obj.getClass());
            if (reflector == null) {
                reflector = new Reflector(obj.getClass());
                clazzMap.put(obj.getClass(), reflector);
            }
            for (int i = 0; i < keys.length; i += 2) {
                Object val = reflector.getGetInvoker(keys[i]).invoke(obj, null);
                if (val != null)
                    reflector.getSetInvoker(keys[i + 1]).invoke(obj, new Object[]{ConstantFactory.me().getUserNameById(Integer.parseInt(val.toString()))});
            }
        }
    }

}
