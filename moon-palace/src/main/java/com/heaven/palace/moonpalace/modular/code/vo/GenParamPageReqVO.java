package com.heaven.palace.moonpalace.modular.code.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "生成参数分页查询-入参")
public class GenParamPageReqVO extends PageVO {
    private static final long serialVersionUID = -3512759127332661140L;

    @ApiModelProperty(notes = "别名")
    private String alias;
}
