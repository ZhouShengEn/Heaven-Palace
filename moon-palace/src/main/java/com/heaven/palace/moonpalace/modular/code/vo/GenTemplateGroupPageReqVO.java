package com.heaven.palace.moonpalace.modular.code.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "模板组分页查询-入参")
public class GenTemplateGroupPageReqVO extends PageVO {
    private static final long serialVersionUID = -750795077475230191L;

    @ApiModelProperty(notes = "模板组名称")
    private String name;
}
