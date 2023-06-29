package com.heaven.palace.moonpalace.modular.custom.authority.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/23 14:23
 */
@Data
@ApiModel(description = "资源新增-入参")
public class ResourceAddReqVO<T extends Model<T>> implements Serializable {
    private static final long serialVersionUID = -4992876900833398278L;

    private T resource;

    private List<Integer> groupIds;

}
