package com.heaven.palace.brightpalace.domain.business.user.aggregate.value;

import cn.hutool.core.util.ReUtil;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValidValueObject;
import com.heaven.palace.purplecloudpalace.util.SHAPasswordEncoder;

/**
 * @Author: zhoushengen
 * @Description: 值对象-密码
 * @DateTime: 2024/1/17 9:59
 **/
public class Password extends ValidValueObject<String> {

    private static final long serialVersionUID = -4848050567279390575L;

    /**
     * 密码正则校验：限制为8-15个字符，必须同时包含数字、英文、特殊符
     */
    public static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^*])[A-Za-z\\d][A-Za-z\\d~!@#$%^*]{7,14}$";

    public Password(String password) {
        super(BusinessExceptionEnum.VALID_PASSWORD_NULL_ERROR
            , BusinessExceptionEnum.VALID_PASSWORD_ERROR, null
            , password, () -> {
                SHAPasswordEncoder shaPasswordEncoder = new SHAPasswordEncoder();
                return shaPasswordEncoder.encode(password);
            });
    }

    @Override
    public Boolean isValid(String password, Object... validArgs) {
        return ReUtil.isMatch(PASSWORD_PATTERN, password);
    }
}
