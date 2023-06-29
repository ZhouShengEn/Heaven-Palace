package com.heaven.palace.moonpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName("custom_module")
@Data
public class CustomModuleModel extends CodeBaseModel<CustomModuleModel>{
    private static final long serialVersionUID = 1719622455507117594L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "MODULE_PARAM")
    private String moduleParam;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "DEFAULT_DB_ID")
    private Integer defaultDbId;


}
