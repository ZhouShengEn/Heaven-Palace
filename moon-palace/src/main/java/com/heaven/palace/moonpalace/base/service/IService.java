package com.heaven.palace.moonpalace.base.service;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 14:39
 */
public interface IService<T extends Model<T>> {

    T add(T t);

    ObjectRestResponse<Void> update(T t);

    void delete(Integer id);

    T selectById(Integer id);
}
