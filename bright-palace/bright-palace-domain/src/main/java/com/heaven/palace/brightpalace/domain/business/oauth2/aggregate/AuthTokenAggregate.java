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
public class AuthTokenAggregate implements AggregateRoot<Token> {

    private static final long serialVersionUID = 8690667130066042384L;
    private Token accessToken;

    private Token freshToken;

    private Token clientToken;

    private UserAggregate userAggregate;

    public AuthTokenAggregate(UserAggregate userAggregate) {
        this.userAggregate = userAggregate;
        this.accessToken = new Token(generateToken());
        this.freshToken = new Token(generateToken());
        this.clientToken = new Token(generateToken());
    }

    public AuthTokenAggregate() {
        this.accessToken = new Token(generateToken());
        this.freshToken = new Token(generateToken());
        this.clientToken = new Token(generateToken());
    }

    /**
     * token生成
     * @return
     */
    public static String generateToken() {
        return IdUtil.fastSimpleUUID();
    }
}
