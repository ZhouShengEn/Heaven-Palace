package com.heaven.palace.purplecloudpalace.modular.system.dao;

import com.heaven.palace.purplecloudpalace.node.MenuNode;
import com.heaven.palace.purplecloudpalace.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 菜单相关的dao
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface MenuDao {

    /**
     * 根据条件查询菜单
     *
     * @date 2022年8月25日
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @date 2022年8月25日
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @date 2022年8月25日
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @date 2022年8月25日
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @date 2022年8月25日
     */
    int deleteRelationByMenu(Integer menuId);

    /**
     * 获取资源url通过角色id
     *
     * @date 2022年8月25日
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @date 2022年8月25日
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);


}
