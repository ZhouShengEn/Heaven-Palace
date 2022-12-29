package com.heaven.palace.purplecloudpalace.modular.system.service;

/**
 * 角色相关业务
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
public interface IRoleService {

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param ids    权限的id
     * @date 2022年8月25日
     */
    void setAuthority(Integer roleId, String ids);

    /**
     * 删除角色
     *
     * @author ZhouShengEn
     * @Date 2017/5/5 22:24
     */
    void delRoleById(Integer roleId);
}
