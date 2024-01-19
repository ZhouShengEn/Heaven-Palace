package com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity;

import com.heaven.palace.jasperpalace.base.ddd.Entity;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 实体-客户端
 * @DateTime: 2024/1/19 17:38
 **/
@Data
@Accessors(chain = true)
public class ClientEntity implements Entity<PrimaryKey> {

    private PrimaryKey id;
    /**
     * 客户端编码
     *
     */
    private String code;
    /**
     * 服务密钥
     *
     */
    private String secret;
    /**
     * 系统登录入口
     *
     */
    private String loginUrl;
    /**
     * 描述
     *
     */
    private String description;

}
