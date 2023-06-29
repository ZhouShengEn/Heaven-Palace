package com.heaven.palace.moonpalace.modular.custom.authority.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/24 9:16
 */
@Data
@ApiModel(description = "资源修改-入参")
public class ResourceUpdateReqVO<T extends Model<T>> implements Serializable {
    private static final long serialVersionUID = 1836825661832092711L;

    @ApiModelProperty(notes = "资源对象")
    private T resource;

    @ApiModelProperty(notes = "初始角色权限id")
    private List<Integer> initGroupIds;

    @ApiModelProperty(notes = "最终角色权限id")
    private List<Integer> finalGroupIds;
}
