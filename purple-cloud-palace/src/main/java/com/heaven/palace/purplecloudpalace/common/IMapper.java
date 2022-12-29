package com.heaven.palace.purplecloudpalace.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author :zhoushengen
 * @date : 2022/9/5
 */
public interface IMapper<T> extends BaseMapper<T> {


    default List<T> queryWithEqualConditions(T t){
        if (null == t) {
            throw new NullPointerException("查询对象不能为空！");
        }
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        // 确定对象属性是否全为null
        AtomicBoolean flag = new AtomicBoolean();
        flag.set(false);
        Arrays.stream(t.getClass().getDeclaredFields()).forEach(field -> {
            try {
                Object condition;
                field.setAccessible(true);
                if ((null != (condition = field.get(t))) && !"serialVersionUID".equals(field.getName())) {
                    wrapper.eq(field.getName(), condition);
                    flag.set(true);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return selectList(wrapper);
    }
}
