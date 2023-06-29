package com.heaven.palace.moonpalace.modular.system.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/17 16:09
 */
@Data
@ApiModel(value = "数据同步请求-入参")
public class SynchronizedEnvReqVO implements Serializable {
    private static final long serialVersionUID = 8804878431651598975L;

    @ApiModelProperty(notes = "同步数据对象")
    private JSONObject data;

    @ApiModelProperty(notes = "目标数据源id", required = true)
    private Integer targetDbId;

    @ApiModelProperty(notes = "业务参数", required = true)
    private String moduleParam;

    @ApiModelProperty(notes = "是否删除同步！", required = true)
    private Boolean isDelete;
}
