package com.heaven.palace.brightpalace.domain.business.permission.aggregate.value;

import com.heaven.palace.brightpalace.api.enums.permission.AuthorityElementConst.Resource.Type;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValueObject;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: zhoushengen
 * @Description: 值对象-系统资源类型
 * @DateTime: 2024/2/2 12:24
 **/
@Getter
public class SystemResourceType implements ValueObject<Integer> {
    private static final long serialVersionUID = 3576323753180250918L;

    private final Type type;

    public SystemResourceType(Integer code) {
        this.type = Arrays.stream(Type.values()).filter(type -> code.equals(type.getCode())).findFirst().orElse(null);
    }


    public Boolean equal(Integer code) {
        if (null == code) {
            throw new BusinessException(BusinessExceptionEnum.VALID_SYSTEM_RESOURCE_TYPE_NULL_ERROR);
        }
        return code.equals(value());
    }

    @Override
    public Integer value() {
        return null == type ? null : type.getCode();
    }
}
