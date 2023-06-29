package com.heaven.palace.moonpalace.modular.system.factory;

import com.heaven.palace.moonpalace.common.persistence.model.User;
import com.heaven.palace.moonpalace.modular.system.transfer.UserDto;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class UserFactory {

    public static User createUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            return user;
        }
    }
}
