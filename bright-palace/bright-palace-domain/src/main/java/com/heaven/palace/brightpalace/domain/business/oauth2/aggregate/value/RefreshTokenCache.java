package com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value;

import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.jasperpalace.base.ddd.Token;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValueObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 值对象-刷新token缓存
 * @DateTime: 2024/2/5 15:49
 **/
@Data
@Accessors(chain = true)
public class RefreshTokenCache implements ValueObject<Token> {
    private static final long serialVersionUID = -5563041396802516995L;

    private Token refreshToken;

    private Token accessToken;

    private UserCache userCache;

    @Override
    public Token value() {
        return refreshToken;
    }
}
