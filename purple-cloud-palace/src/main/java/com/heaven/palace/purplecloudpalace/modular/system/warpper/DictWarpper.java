package com.heaven.palace.purplecloudpalace.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.base.warpper.BaseControllerWarpper;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Dict;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;


/**
 * 字典列表的包装
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Integer id = (Integer) map.get("id");
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if (dicts != null) {
            for (Dict dict : dicts) {
                detail.append(dict.getNum() + ":" + dict.getName() + ",");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(), ","));
        }
    }

}
