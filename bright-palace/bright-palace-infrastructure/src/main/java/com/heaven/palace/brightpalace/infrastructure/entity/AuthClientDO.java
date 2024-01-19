package com.heaven.palace.brightpalace.infrastructure.entity;

import com.heaven.palace.jasperpalace.base.entity.BaseEntity;
import com.heaven.palace.purplecloudpalace.listen.db.DbInsertListener;
import com.heaven.palace.purplecloudpalace.listen.db.DbUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 认证客户端
 * @DateTime: 2024/1/19 17:39
 **/
@Table(value = "auth_client", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthClientDO extends BaseEntity {
    private static final long serialVersionUID = 284973579804412838L;

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
    /**
     * true-已删除，false-未删除
     *
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
