package com.heaven.palace.brightpalace.domain.business.user.aggregate.value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.ValidValueObject;
import lombok.Getter;

/**
 * @Author: zhoushengen
 * @Description: 值对象-用户名
 * @DateTime: 2024/1/16 13:02
 **/
@Getter
public class Username extends ValidValueObject<String> {

    /**
     * 用户名正则校验：限制字符长度6~20，可包含数字、中文、字母、连字符(-)、下划线(_)
     */
    public static final String USERNAME_PATTERN = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{6,20}$";

    public Username(String username) {
        super(BusinessExceptionEnum.REGISTER_USERNAME_NULL
            , BusinessExceptionEnum.REGISTER_USERNAME_VALID_ERROR
            , username, null);
    }


    @Override
    public Boolean isValid(String username) {
        return ReUtil.isMatch(USERNAME_PATTERN, username);
    }
}
