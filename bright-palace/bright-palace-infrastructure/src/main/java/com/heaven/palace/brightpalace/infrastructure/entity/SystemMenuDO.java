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
 * SystemMenu
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:46
 */
@Table(value = "system_menu", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemMenuDO extends BaseEntity {

    private static final long serialVersionUID = 7628037020567460811L;
    /**
     * 标题
     * 
     */
    private String title;
    /**
     * 资源路径
     * 
     */
    private String href;
    /**
     * 图标
     * 
     */
    private String icon;
    /**
     * 父级菜单id
     * 
     */
    private Long parentId;
    /**
     * 菜单级别
     * 
     */
    private Integer level;
    /**
     * 类型：0-系统；1-路由；2-菜单
     * 
     */
    private Integer type;
    /**
     * 状态：0-禁用；1-启用
     * 
     */
    private Integer status;
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
