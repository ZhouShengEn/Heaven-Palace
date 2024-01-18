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
 * UserRole
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:45
 */
@Table(value = "user_role", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleDO extends BaseEntity {

    private static final long serialVersionUID = 2768242548759081552L;
    /**
     * 角色名称
     * 
     */
    private String roleName;
    /**
     * 角色编码
     *
     */
    private String code;
    /**
     * 组织id
     *
     */
    private Long orgId;
    /**
     * 父级角色id
     *
     */
    private Long parentId;
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
