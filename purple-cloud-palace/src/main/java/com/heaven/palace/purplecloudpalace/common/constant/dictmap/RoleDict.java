package com.heaven.palace.purplecloudpalace.common.constant.dictmap;

import com.heaven.palace.purplecloudpalace.common.constant.dictmap.base.AbstractDictMap;

/**
 * 角色的字典
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class RoleDict extends AbstractDictMap {

    @Override
    public void init() {
        put("roleId", "角色名称");
        put("num", "角色排序");
        put("pid", "角色的父级");
        put("name", "角色名称");
        put("deptid", "部门名称");
        put("tips", "备注");
        put("ids", "资源名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("pid", "getSingleRoleName");
        putFieldWrapperMethodName("deptid", "getDeptName");
        putFieldWrapperMethodName("roleId", "getSingleRoleName");
        putFieldWrapperMethodName("ids", "getMenuNames");
    }
}
