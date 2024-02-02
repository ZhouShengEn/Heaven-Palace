package com.heaven.palace.brightpalace.domain.business.user.aggregate.value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValidValueObject;

/**
 * @Author: zhoushengen
 * @Description: 值对象-移动手机号
 * @DateTime: 2024/1/17 15:46
 **/
public class MobilePhone extends ValidValueObject<String> {

    private static final long serialVersionUID = -8653329965698391358L;

    public final static String PHONE_NUMBER_PATTERN = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    public MobilePhone(String phoneNumber) {
        // todo 手机号解密
        super(BusinessExceptionEnum.VALID_MOBILE_PHONE_NULL_ERROR, BusinessExceptionEnum.VALID_MOBILE_PHONE_ERROR,
            phoneNumber);
    }

    @Override
    public Boolean isValid(String phoneNumber, Object... validArgs) {
        return ReUtil.isMatch(PHONE_NUMBER_PATTERN, phoneNumber);
    }
}
