package com.heaven.palace.brightpalace.domain.business.user.aggregate.Value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BrightPalaceBusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.ValidValueObject;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Getter;

/**
 * @Author: zhoushengen
 * @Description: 值对象-用户名
 * @DateTime: 2024/1/16 13:02
 **/
@Getter
public class Username extends ValidValueObject<String> {

    public static final String USERNAME_PATTERN = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]{0,20}$";

    /**
     * 用户名
     */
    private final String username;


    public Username(String username) {
        super(new BusinessException(BrightPalaceBusinessExceptionEnum.REGISTER_USERNAME_NULL.getCode(),
            BrightPalaceBusinessExceptionEnum.REGISTER_USERNAME_NULL.getMessage())
            , new BusinessException(BrightPalaceBusinessExceptionEnum.REGISTER_USERNAME_VALID_ERROR.getCode(),
            BrightPalaceBusinessExceptionEnum.REGISTER_USERNAME_VALID_ERROR.getMessage()), username);
        this.username = username;
    }


    @Override
    public Boolean isValid(String username) {
        return ReUtil.isMatch(USERNAME_PATTERN, username);
    }
}
