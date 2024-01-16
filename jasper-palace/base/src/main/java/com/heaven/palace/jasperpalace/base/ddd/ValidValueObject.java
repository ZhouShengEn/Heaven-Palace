package com.heaven.palace.jasperpalace.base.ddd;

import com.heaven.palace.jasperpalace.base.ddd.Value.ValueObject;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import org.springframework.util.ObjectUtils;

/**
 * @Author: zhoushengen
 * @Description: 校验值对象
 * @DateTime: 2024/1/16 13:05
 **/
public abstract class ValidValueObject<T> implements ValueObject<T> {
    public abstract Boolean isValid(T t);

    public ValidValueObject(BusinessException nullException, BusinessException validException, T value) {
        if (ObjectUtils.isEmpty(value)) {
            throw nullException;
        }
        if (!isValid(value)) {
            throw validException;
        }
    }
}
