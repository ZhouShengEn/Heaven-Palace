package com.heaven.palace.moonpalace.modular.system.service;

/**
 * 菜单服务
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public interface IMenuService {

    /**
     * 删除菜单
     *
     * @author ZhouShengEn
     * @Date 2017/5/5 22:20
     */
    void delMenu(Integer menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     * @author ZhouShengEn
     * @Date 2017/6/13 22:02
     */
    void delMenuContainSubMenus(Integer menuId);
}
