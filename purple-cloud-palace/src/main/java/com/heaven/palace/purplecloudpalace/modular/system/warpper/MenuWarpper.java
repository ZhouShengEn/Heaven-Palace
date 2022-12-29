package com.heaven.palace.purplecloudpalace.modular.system.warpper;

import com.heaven.palace.purplecloudpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.purplecloudpalace.common.constant.state.IsMenu;

import java.util.List;
import java.util.Map;

/**
 * 菜单列表的包装类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class MenuWarpper extends BaseControllerWarpper {

    public MenuWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }

}
