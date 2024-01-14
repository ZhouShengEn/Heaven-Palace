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
 * SystemResource
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:46
 */
@Table(value = "system_resource", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class SystemResource extends BaseEntity {

    private static final long serialVersionUID = 144466244946845603L;
    /**
     * 资源编码
     * 
     */
    private String code;
    /**
     * 资源名称
     * 
     */
    private String name;
    /**
     * 资源数据
     * 
     */
    private String value;
    /**
     * 资源类型：0-uri
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
