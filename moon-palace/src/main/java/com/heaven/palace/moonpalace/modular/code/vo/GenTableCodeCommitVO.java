package com.heaven.palace.moonpalace.modular.code.vo;

import com.heaven.palace.moonpalace.model.GenerationEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "表生成代码提交")
public class GenTableCodeCommitVO extends GenerationEntity {
    private static final long serialVersionUID = 4457828823632842422L;

    @ApiModelProperty(notes = "数据表Id")
    private Integer tableId;

    @ApiModelProperty(notes = "模板ids")
    private String[] templateIds;

}
