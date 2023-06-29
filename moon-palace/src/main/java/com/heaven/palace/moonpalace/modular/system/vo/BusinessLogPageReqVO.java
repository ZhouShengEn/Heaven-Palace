package com.heaven.palace.moonpalace.modular.system.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/16 17:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessLogPageReqVO extends PageVO {
    private static final long serialVersionUID = 530968656293031860L;

    @ApiModelProperty(notes = "新增、修改、删除")
    private List<String> titles;

    @ApiModelProperty(notes = "业务类型")
    private String type;

    @ApiModelProperty(notes = "数据源id")
    private Integer dbId;

    @ApiModelProperty(notes = "同步状态：0-不需要同步；1-需要同步；2-已同步")
    private Integer syncStatus;

    @ApiModelProperty(notes = "用户id")
    private Integer userId;

    @ApiModelProperty(notes = "父级id")
    private Integer parentId;



}
