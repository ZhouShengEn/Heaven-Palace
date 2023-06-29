package com.heaven.palace.moonpalace.modular.system.warpper;

import com.heaven.palace.moonpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.moonpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.moonpalace.util.ToolUtil;

import java.util.Map;


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
