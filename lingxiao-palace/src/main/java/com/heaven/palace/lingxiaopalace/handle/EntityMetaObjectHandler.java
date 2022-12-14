package com.heaven.palace.lingxiaopalace.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heaven.palace.lingxiaopalace.constant.CommonConst;
import com.heaven.palace.lingxiaopalace.context.CurrentBaseContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author :zhoushengen
 * @date : 2022/12/30
 */
public class EntityMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = Date.from(ZonedDateTime.now().toInstant());
        String userId = CurrentBaseContext.get(CommonConst.KEY_USER_ID).toString();
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("createdTime", now, metaObject);
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = CurrentBaseContext.get(CommonConst.KEY_USER_ID).toString();
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", Date.from(ZonedDateTime.now().toInstant()), metaObject);
    }
}
