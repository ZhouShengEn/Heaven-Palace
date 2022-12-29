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
public class BaseDictionaryTypeVo {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 类型编码
     */
    private String code;

    /**
     * 字典类型名称
     */
    private String text;


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
