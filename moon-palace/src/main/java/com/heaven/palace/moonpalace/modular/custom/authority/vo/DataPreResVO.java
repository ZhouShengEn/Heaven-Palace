package com.heaven.palace.moonpalace.modular.custom.authority.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.heaven.palace.moonpalace.modular.custom.ugroup.vo.CustomUserGroupSwitchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/22 17:37
 */
@Data
@ApiModel(description = "前置数据结构")
public class DataPreResVO<T extends Model<T>> implements Serializable {
    private static final long serialVersionUID = 4961694303209899648L;

    @ApiModelProperty(notes = "资源数据")
    private T resource;

    @ApiModelProperty(notes = "角色类型名称 -> 资源权限开关")
    private Map<String, List<CustomUserGroupSwitchVO>> switches;
}
