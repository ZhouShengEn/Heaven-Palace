package com.heaven.palace.brightpalaceinfrastructure.entity;

import com.heaven.palace.jasperpalace.base.entity.BaseEntity;
import com.heaven.palace.purplecloudpalace.listen.db.DbInsertListener;
import com.heaven.palace.purplecloudpalace.listen.db.DbUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * BaseUser
 * 基础用户信息实体
 * @author ZhouShengEn
 * @date 2024-01-09 17:55
 */
@EqualsAndHashCode(callSuper = true)
@Table(value = "base_user", onInsert = DbInsertListener.class, onUpdate = DbUpdateListener.class)
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class BaseUser extends BaseEntity {

    private static final long serialVersionUID = 846218296240178591L;
    /**
     * 用户名称
     * 
     */
    private String username;
    /**
     * 昵称
     * 
     */
    private String nickname;
    /**
     * 用户密码
     * 
     */
    private String password;
    /**
     * 用户生日
     * 
     */
    private String birthday;
    /**
     * 地址
     * 
     */
    private String address;
    /**
     * 移动手机号
     * 
     */
    private String mobilePhone;
    /**
     * 电话号码
     * 
     */
    private String telPhone;
    /**
     * 邮箱
     * 
     */
    private String email;
    /**
     * 头像图片地址
     *
     */
    private String avatar;
    /**
     * 性别：0-男；1-女
     * 
     */

    private Integer sex;
    /**
     * 状态：0-使用中
     * 
     */
    private Integer status;
    /**
     * true-已删除，false-未删除
     * 
     */
    @Column(isLogicDelete = true)
    private Boolean isDelete;

}
