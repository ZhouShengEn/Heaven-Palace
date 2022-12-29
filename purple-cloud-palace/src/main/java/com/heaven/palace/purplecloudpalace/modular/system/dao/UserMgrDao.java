package com.heaven.palace.purplecloudpalace.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.common.persistence.model.User;
import com.heaven.palace.purplecloudpalace.core.datascope.DataScope;

/**
 * 管理员的dao
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface UserMgrDao {

    /**
     * 修改用户状态
     *
     * @date 2022年8月25日
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     *
     * @date 2022年8月25日
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     *
     * @date 2022年8月25日
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

    /**
     * 设置用户的角色
     *
     * @date 2022年8月25日
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     *
     * @date 2022年8月25日
     */
    User getByAccount(@Param("account") String account);
}
