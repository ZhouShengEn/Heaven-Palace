package com.heaven.palace.purplecloudpalace.core.util;

import java.util.ArrayList;
import java.util.List;

import com.heaven.palace.purplecloudpalace.common.constant.Const;
import com.heaven.palace.purplecloudpalace.config.properties.GunsProperties;
import com.heaven.palace.purplecloudpalace.node.MenuNode;
import com.heaven.palace.purplecloudpalace.util.SpringContextHolder;

/**
 * api接口文档显示过滤
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class ApiMenuFilter extends MenuNode {


    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
        GunsProperties gunsProperties = SpringContextHolder.getBean(GunsProperties.class);
        if (!gunsProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<MenuNode>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
