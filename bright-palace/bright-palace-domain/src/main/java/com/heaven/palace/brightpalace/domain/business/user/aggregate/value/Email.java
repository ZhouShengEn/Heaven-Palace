package com.heaven.palace.brightpalace.domain.business.user.aggregate.value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValidValueObject;

/**
 * @Author: zhoushengen
 * @Description: 值对象-邮箱
 * @DateTime: 2024/1/17 15:52
 **/
public class Email extends ValidValueObject<String> {

    private static final long serialVersionUID = -925080582639134418L;

    public static final String EMAIL_PATTERN = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";

    public Email(String email) {
        super(BusinessExceptionEnum.VALID_EMAIL_NULL_ERROR
            , BusinessExceptionEnum.VALID_EMAIL_ERROR
            , email);
    }

    @Override
    public Boolean isValid(String email, Object... validArgs) {
        return ReUtil.isMatch(EMAIL_PATTERN, email);
    }
}
