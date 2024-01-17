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
 * UserOrganization
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:45
 */
@Table(value = "user_organization", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class UserOrganizationDO extends BaseEntity {

    private static final long serialVersionUID = -7929630669927545847L;
    /**
     * 组织名称
     * 
     */
    private String name;
    /**
     * 描述
     * 
     */
    private String description;
    /**
     * 父级组织id
     * 
     */
    private Long parentId;
    /**
     * true-已删除，false-未删除
     * 
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
