package com.heaven.palace.purplecloudpalace.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;

/**
 * 角色列表的包装类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
