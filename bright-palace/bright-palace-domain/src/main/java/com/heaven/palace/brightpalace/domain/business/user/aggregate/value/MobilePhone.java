package com.heaven.palace.brightpalace.domain.business.user.aggregate.value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.ValidValueObject;

/**
 * @Author: zhoushengen
 * @Description: 值对象-移动手机号
 * @DateTime: 2024/1/17 15:46
 **/
public class MobilePhone extends ValidValueObject<String> {

    public final static String PHONE_NUMBER_PATTERN = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public MobilePhone(String phoneNumber) {
        super(BusinessExceptionEnum.REGISTER_MOBILE_PHONE_NULL, BusinessExceptionEnum.REGISTER_MOBILE_PHONE_VALID_ERROR,
            phoneNumber, null);
    }

    @Override
    public Boolean isValid(String phoneNumber) {
        return ReUtil.isMatch(PHONE_NUMBER_PATTERN, phoneNumber);
    }
}
