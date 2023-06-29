package com.heaven.palace.moonpalace.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :zhoushengen
 * @date : 2023/2/2
 */
@Data
public class PageVO implements Serializable {
    private static final long serialVersionUID = -2914133381040862667L;
    
    @ApiModelProperty(notes = "每页条数，最小值为1", required = true)
    @Range(min = 1, message = "每页条数最小值为1")
    @NotNull(message = "每页条数不能为空")
    private Integer limit;

    @ApiModelProperty(notes = "当前页码，最小值为1", required = true)
    @Range(min = 1, message = "页码最小值为1")
    @NotNull(message = "页码不能为空")
    private Integer page;

    private String sortBy;
}
