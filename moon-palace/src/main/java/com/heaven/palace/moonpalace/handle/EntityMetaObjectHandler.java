package com.heaven.palace.moonpalace.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author :zhoushengen
 * @date : 2022/12/30
 * mybatis-plus 配置数据插入更新策略
 */
public class EntityMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = Date.from(ZonedDateTime.now().toInstant());
        String userId = String.valueOf(CurrentBaseContext.getUserId());
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("createdTime", now, metaObject);
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = String.valueOf(CurrentBaseContext.getUserId());
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", Date.from(ZonedDateTime.now().toInstant()), metaObject);
    }
}
