package com.heaven.palace.purplecloudpalace.modular.system.warpper;

import java.util.Map;

import com.heaven.palace.purplecloudpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;


/**
 * 部门列表的包装
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}
