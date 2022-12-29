package com.heaven.palace.purplecloudpalace.modular.system.dao;

import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 角色相关的dao
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface RoleDao {

    /**
     * 根据条件查询角色列表
     *
     * @date 2022年8月25日
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @date 2022年8月25日
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @date 2022年8月25日
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     *
     * @date 2022年8月25日
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);


}
