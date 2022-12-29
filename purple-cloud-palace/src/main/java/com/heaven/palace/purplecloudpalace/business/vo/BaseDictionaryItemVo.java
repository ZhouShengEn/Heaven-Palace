package com.heaven.palace.purplecloudpalace.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * @author :zhoushengen
 * @date : 2022/9/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDictionaryItemVo {
    /**
     * 枚举值id
     */
    private Integer id;
    
    /**
     * 序号
     */
    private Integer dictSort;

    /**
     * 父枚举值id
     */
    private Integer parentId;
    
    /**
     * 层级关系
     */
    private String level;

    /**
     * 展示名
     */
    private String text;

    /**
     * 类型编码
     */
    private String typeCode;
    /**
     * 类型Id
     */
    private String typeId;

    /**
     * 字典类型名称
     */
    private String typeText;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典值
     */
    private String value;

    /**
     * 创建人
     */
    @DiffIgnore
    private String createBy;

    /**
     * 创建时间
     */
    @DiffIgnore
    private String createTime;

    /**
     * 修改人
     */
    @DiffIgnore
    private String updateBy;

    /**
     * 修改时间
     */
    @DiffIgnore
    private String updateDate;
}
