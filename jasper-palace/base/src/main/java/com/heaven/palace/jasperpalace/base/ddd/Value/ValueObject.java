package com.heaven.palace.jasperpalace.base.ddd.Value;

import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 值对象
 * @DateTime: 2024/1/16 17:50
 **/
public interface ValueObject<T> extends Serializable {

    T value();
}
