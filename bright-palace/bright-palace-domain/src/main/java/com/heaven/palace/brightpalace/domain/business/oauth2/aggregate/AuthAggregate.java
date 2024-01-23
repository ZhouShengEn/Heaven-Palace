package com.heaven.palace.brightpalace.domain.business.oauth2.aggregate;

import cn.hutool.core.util.IdUtil;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.jasperpalace.base.ddd.AggregateRoot;
import com.heaven.palace.jasperpalace.base.ddd.Token;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 认证聚合根
 * @DateTime: 2024/1/23 16:47
 **/
@Data
@Accessors(chain = true)
public class AuthAggregate implements AggregateRoot<Token> {

    private Token accessToken;

    private Token freshToken;

    private UserAggregate userAggregate;

    public AuthAggregate(UserAggregate userAggregate) {
        this.userAggregate = userAggregate;
        this.accessToken = new Token(generateToken());
        this.freshToken = new Token(generateToken());
    }

    public AuthAggregate() {
        this.accessToken = new Token(generateToken());
        this.freshToken = new Token(generateToken());
    }

    /**
     * token生成
     * @return
     */
    public static String generateToken() {
        return IdUtil.fastSimpleUUID();
    }
}
