package com.heaven.palace.moonpalace.modular.system.warpper;

import com.heaven.palace.moonpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.moonpalace.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
