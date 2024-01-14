package com.heaven.palace.brightpalaceinfrastructure.entity;

import com.heaven.palace.jasperpalace.base.entity.BaseEntity;
import com.heaven.palace.purplecloudpalace.listen.db.DbInsertListener;
import com.heaven.palace.purplecloudpalace.listen.db.DbUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * AuthorityResourceMenu
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:44
 */
@Table(value = "authority_resource_menu", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class AuthorityResourceMenu extends BaseEntity {

    private static final long serialVersionUID = 3348008165275559813L;
    /**
     * 角色id
     * 
     */
    private Long roleId;
    /**
     * 元素id
     * 
     */
    private Long elementId;
    /**
     * 元素类型：0-resource；1-menu
     * 
     */
    private Integer elementType;
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
