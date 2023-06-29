package com.heaven.palace.moonpalace.modular.code.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "模板分页查询-入参")
public class GenTemplatePageReqVO extends PageVO {
    private static final long serialVersionUID = 8292984237265172179L;

    /**
     * 模板名称
     */
    @ApiModelProperty(notes = "模板名称")
    private String templateName;
    /**
     * 组ID
     */
    @ApiModelProperty(notes = "模板组id")
    private String groupId;
    /**
     * 模板类型
     */
    @ApiModelProperty(notes = "模板类型")
    private String templateType;

}
