package com.heaven.palace.brightpalaceapi.api.user.dto;

import com.heaven.palace.jasperpalace.base.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 10733
 * @date 2024/1/14 22:15
 * @description: 用户认证信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户认证信息")
public class UserAuthDTO extends BaseDTO {
    private static final long serialVersionUID = -2738597649082166217L;
}
