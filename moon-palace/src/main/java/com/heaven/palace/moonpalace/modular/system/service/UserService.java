package com.heaven.palace.moonpalace.modular.system.service;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.modular.system.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    /**
     * 上传头像
     * @param avatar
     * @return
     */
    ObjectRestResponse<String> uploadAvatar(MultipartFile avatar);

    /**
     * 获取用户信息
     * @return
     */
    UserVO getCurrentUser();
}
