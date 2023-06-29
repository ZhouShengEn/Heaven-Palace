package com.heaven.palace.moonpalace.modular.code.vo;

import com.heaven.palace.moonpalace.model.GenerationEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "生成代码提交")
public class GenCodeCommitVO extends GenerationEntity {
    private static final long serialVersionUID = 4457828823632842422L;

    @ApiModelProperty(notes = "数据源id")
    private Integer dbId;

    @ApiModelProperty(notes = "数据库名称")
    private String dbName;

    @ApiModelProperty(notes = "数据表名称")
    private String tableName;

    @ApiModelProperty(notes = "模板ids")
    private String[] templateIds;

}
