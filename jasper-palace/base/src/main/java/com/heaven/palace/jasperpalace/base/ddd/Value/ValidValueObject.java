package com.heaven.palace.jasperpalace.base.ddd.Value;

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

    private static final long serialVersionUID = -3350427360984629334L;
    private final T value;

    public abstract Boolean isValid(T t, Object... validArgs);

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, BaseResult afterCallErrorResult,
        T value, Callable<T> afterValid) {
        BusinessException.throwWith(ObjectUtils.isEmpty(value), nullResult);
        BusinessException.throwWith(!isValid(value), validErrorResult);
        try {
            this.value = (null != afterValid ? afterValid.call() : value);
        } catch (Exception e) {
            throw new BusinessException(afterCallErrorResult);
        }
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value) {
        BusinessException.throwWith(ObjectUtils.isEmpty(value), nullResult);
        BusinessException.throwWith(!isValid(value), validErrorResult);
        this.value = value;
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult, T value, Object... validArgs) {
        BusinessException.throwWith(ObjectUtils.isEmpty(value), nullResult);
        BusinessException.throwWith(!isValid(value, validArgs), validErrorResult);
        this.value = value;
    }

    public ValidValueObject(BaseResult nullResult, BaseResult validErrorResult
        , BaseResult afterCallErrorResult, T value, Callable<T> afterValid, Object... validArgs) {
        BusinessException.throwWith(ObjectUtils.isEmpty(value), nullResult);
        BusinessException.throwWith(!isValid(value, validArgs), validErrorResult);
        try {
            this.value = (null != afterValid ? afterValid.call() : value);
        } catch (Exception e) {
           throw new BusinessException(afterCallErrorResult);
        }
    }

    @Override
    public T value() {
        return this.value;
    }
}
