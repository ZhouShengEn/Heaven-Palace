package com.heaven.palace.brightpalace.domain.business.permission.aggregate.value;

import com.heaven.palace.brightpalace.api.enums.permission.AuthorityElementConst.Resource.Status;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValueObject;

import java.util.Arrays;

/**
 * @Author: zhoushengen
 * @Description: 值对象-系统资源状态
 * @DateTime: 2024/2/2 12:24
 **/
public class SystemResourceStatus implements ValueObject<Integer> {
    private static final long serialVersionUID = 3576323753180250918L;

    private final Status status;

    public SystemResourceStatus(Integer code) {
        this.status = Arrays.stream(Status.values()).filter(type -> code.equals(type.getCode())).findFirst().orElse(null);
    }

    public Boolean checkEnable() {
        Integer code = value();
        return Status.ENABLE.getCode().equals(code);
    }

    @Override
    public Integer value() {
        return null == status ? null : status.getCode();
    }
}
