package com.heaven.palace.jasperpalace.base.ddd;

import com.heaven.palace.jasperpalace.base.ddd.Value.ValueObject;
import com.heaven.palace.jasperpalace.base.exception.BaseResult;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.Callable;

/**
 * @Author: zhoushengen
 * @Description: 校验值对象
 * @DateTime: 2024/1/16 13:05
 **/
@Getter
public abstract class ValidValueObject<T> implements ValueObject<T> {

    private final T value;

    public abstract Boolean isValid(T t, Object... validArgs);

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value, Callable<T> afterValid) {
        if (ObjectUtils.isEmpty(value)) {
            throw new BusinessException(nullResult);
        }
        if (!isValid(value)) {
            throw new BusinessException(validErrorResult);
        }
        try {
            this.value = (null != afterValid ? afterValid.call() : value);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw new BusinessException(((BusinessException) e).getStatusCode(), e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value) {
        if (ObjectUtils.isEmpty(value)) {
            throw new BusinessException(nullResult);
        }
        if (!isValid(value)) {
            throw new BusinessException(validErrorResult);
        }
        this.value = value;
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value, Object... validArgs) {
        if (ObjectUtils.isEmpty(value)) {
            throw new BusinessException(nullResult);
        }
        if (!isValid(value, validArgs)) {
            throw new BusinessException(validErrorResult);
        }
        this.value = value;
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value, Callable<T> afterValid, Object... validArgs) {

        if (ObjectUtils.isEmpty(value)) {
            throw new BusinessException(nullResult);
        }
        if (!isValid(value, validArgs)) {
            throw new BusinessException(validErrorResult);
        }
        try {
            this.value = (null != afterValid ? afterValid.call() : value);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw new BusinessException(((BusinessException) e).getStatusCode(), e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public T value() {
        return this.value;
    }
}
