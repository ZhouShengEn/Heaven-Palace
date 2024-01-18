package com.heaven.palace.purplecloudpalace.listen.db;

import com.heaven.palace.jasperpalace.base.entity.BaseEntity;
import com.mybatisflex.annotation.AbstractInsertListener;

/**
 * @Author: zhoushengen
 * @Description: db数据新增监听器
 * @DateTime: 2024/1/11 9:20
 **/
public class DbInsertListener extends AbstractInsertListener<BaseEntity> {

    @Override
    public Class<BaseEntity> supportType() {
        return BaseEntity.class;
    }

    @Override
    public void doInsert(BaseEntity entity) {
        BaseEntity.assembleBaseInfo(entity);
    }
}
