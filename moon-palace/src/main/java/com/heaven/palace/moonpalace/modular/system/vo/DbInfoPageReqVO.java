package com.heaven.palace.moonpalace.modular.system.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author :zhoushengen
 * @date : 2023/2/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "分页查询数据库-请求")
public class DbInfoPageReqVO extends PageVO {
    private static final long serialVersionUID = 7739500222889698860L;

    @ApiModelProperty(notes = "主键id")
    private Integer id;

    @ApiModelProperty(notes = "别名，支持模糊查询")
    private String alias;

    @ApiModelProperty(notes = "数据库驱动，支持模糊查询")
    private String dbDriver;

    @ApiModelProperty(notes = "数据库类型")
    private String dbType;

    @ApiModelProperty(notes = "标签")
    private String tag;

    @ApiModelProperty(notes = "数据源来源分类：0-业务数据源； 1-代码生成数据源")
    private Integer category;


    
}
