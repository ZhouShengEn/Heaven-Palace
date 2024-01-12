package com.heaven.palace.purplecloudpalace.listen.db;

import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.entity.BaseEntity;
import com.mybatisflex.annotation.AbstractUpdateListener;

import java.util.Date;

/**
 * @Author: zhoushengen
 * @Description: db数据更新监听器
 * @DateTime: 2024/1/11 9:40
 **/
public class DbUpdateListener extends AbstractUpdateListener<BaseEntity> {
    @Override
    public Class<BaseEntity> supportType() {
        return null;
    }

    @Override
    public void doUpdate(BaseEntity entity) {
        long userId = CurrentBaseContext.getUserId();
        Date nowDate = new Date();
        entity.setUpdateBy(userId);
        entity.setUpdateTime(nowDate);
    }
}
