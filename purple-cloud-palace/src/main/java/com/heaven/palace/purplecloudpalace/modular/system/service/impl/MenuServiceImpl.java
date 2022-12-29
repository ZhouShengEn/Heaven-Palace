package com.heaven.palace.purplecloudpalace.modular.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.common.persistence.dao.MenuMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Menu;
import com.heaven.palace.purplecloudpalace.modular.system.dao.MenuDao;
import com.heaven.palace.purplecloudpalace.modular.system.service.IMenuService;

/**
 * 菜单服务
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;

    @Override
    public void delMenu(Integer menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuDao.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(Integer menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        QueryWrapper<Menu> wrapper = new QueryWrapper<Menu>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }
}
