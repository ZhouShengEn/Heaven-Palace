package com.heaven.palace.brightpalace.domain.business.permission.aggregate.entity;

import com.heaven.palace.brightpalace.domain.business.permission.aggregate.value.SystemResourceStatus;
import com.heaven.palace.brightpalace.domain.business.permission.aggregate.value.SystemResourceType;
import com.heaven.palace.jasperpalace.base.ddd.Entity;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @Author: zhoushengen
 * @Description: 实体-用户角色资源权限
 * @DateTime: 2024/1/31 16:44
 **/
@Data
@Accessors(chain = true)
public class UserRoleResource implements Entity<PrimaryKey> {
    private static final long serialVersionUID = -9073897680101550386L;

    /**
     * CheckResourcePermission方法后缀
     */
    private static final String CHECK_RESOURCE_PERMISSION_METHOD_SUFFIX = "CheckResourcePermission";

    /**
     * 用户id
     */
    private PrimaryKey userId;

    /**
     * 角色id
     */
    private PrimaryKey roleId;

    /**
     * 资源id
     */
    private PrimaryKey resourceId;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源值
     */
    private String resourceValue;

    /**
     * 资源类型
     */
    private SystemResourceType resourceType;

    /**
     * 资源状态
     */
    private SystemResourceStatus resourceStatus;


    /**
     * 检查资源权限
     * @param matchValue
     * @return
     */
    public Boolean checkResourcePermission(String matchValue) {
        if (!resourceStatus.checkEnable()) {
            return Boolean.FALSE;
        }
        try {
            Method method = this.getClass()
                .getMethod(resourceType.getType().name().toLowerCase()
                    .concat(CHECK_RESOURCE_PERMISSION_METHOD_SUFFIX), String.class);
            return (Boolean) method.invoke(this, matchValue);
        }  catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof BusinessException) {
                throw (BusinessException)cause;
            }
            throw new RuntimeException(e);
        }
    }


    /**
     * 检查uri资源权限
     * @param matchValue
     * @return
     */
    public Boolean uriCheckResourcePermission(String matchValue) {
        if (resourceValue.indexOf("{") > 0) {
            resourceValue = resourceValue.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
        }
        String regEx = "^" + resourceValue + "$";
        try {
            return Pattern.compile(regEx).matcher(matchValue).find();
        } catch (Exception e) {
            return false;
        }
    }


}
