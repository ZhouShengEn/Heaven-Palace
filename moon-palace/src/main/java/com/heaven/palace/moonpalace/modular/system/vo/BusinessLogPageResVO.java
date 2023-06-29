package com.heaven.palace.moonpalace.modular.system.vo;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/24 9:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "业务日志分页查询-出参")
public class BusinessLogPageResVO extends BusinessOperationLog {
    private static final long serialVersionUID = 8005955985317546960L;

    @ApiModelProperty(notes = "数据库别名")
    private String dbAlias;

    @ApiModelProperty(notes = "子日志集合")
    private List<BusinessLogPageResVO> childLogs;
}
