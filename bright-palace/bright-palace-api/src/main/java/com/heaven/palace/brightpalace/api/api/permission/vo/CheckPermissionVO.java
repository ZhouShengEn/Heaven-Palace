package com.heaven.palace.brightpalace.api.api.permission.vo;

import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author: zhoushengen
 * @Description: 权限检查-出参
 * @DateTime: 2024/2/2 17:05
 **/
@Data
public class CheckPermissionVO implements Serializable {
    private static final long serialVersionUID = -1467896384765067862L;

    private Boolean hasPermission;

    private HashMap<String, Object> baseContext;

    private BusinessException errorResult;
}
