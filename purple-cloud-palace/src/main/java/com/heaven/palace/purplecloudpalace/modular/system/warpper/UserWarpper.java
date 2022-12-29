package com.heaven.palace.purplecloudpalace.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;

/**
 * 用户管理的包装类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
    }

}
