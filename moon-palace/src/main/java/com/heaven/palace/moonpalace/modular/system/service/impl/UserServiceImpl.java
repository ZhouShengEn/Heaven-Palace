package com.heaven.palace.moonpalace.modular.system.service.impl;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.common.persistence.dao.DeptMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.RoleMapper;
import com.heaven.palace.moonpalace.common.persistence.dao.UserMapper;
import com.heaven.palace.moonpalace.common.persistence.model.Dept;
import com.heaven.palace.moonpalace.common.persistence.model.Role;
import com.heaven.palace.moonpalace.common.persistence.model.User;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.core.shiro.ShiroUser;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.system.service.UserService;
import com.heaven.palace.moonpalace.modular.system.vo.UserVO;
import com.heaven.palace.moonpalace.util.MappingUtils;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String UPLOAD_AVATAR_ABSOLUTE_PATH
        = "avatar".concat("/");

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MinioClient minioClient;

    @Resource
    private DeptMapper departmentMapper;

    @Resource
    private RoleMapper roleMapper;

    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public ObjectRestResponse<String> uploadAvatar(MultipartFile avatar) {
        if (avatar != null && !avatar.isEmpty()) {
            ShiroUser currentUser = ShiroKit.getUser();
            Integer userId = currentUser.getId();
            String userPath = String.valueOf(userId).concat("/");
            String originalFilename = avatar.getOriginalFilename();
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String filePath =
                UPLOAD_AVATAR_ABSOLUTE_PATH.concat(userPath).concat(currentUser.getAccount().concat(".").concat(fileSuffix));
            try {
                minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(filePath).stream(
                            avatar.getInputStream(), avatar.getSize(), -1)
                        .build());
                GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(filePath)
                    // .expiry(expires)
                    .build();
                String presignedObjectUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
                String userAvatarPath = presignedObjectUrl.split("\\?")[0];
                User user = new User();
                user.setId(userId);
                user.setAvatar(userAvatarPath);
                userMapper.updateById(user);
                return new ObjectRestResponse<>().message("上传成功！").data(userAvatarPath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(BusinessExceptionEnum.FILE_UPLOAD_ERROR);
            }
        }
        throw new BusinessException(BusinessExceptionEnum.FILE_UPLOAD_NULL);
    }

    @Override
    public UserVO getCurrentUser() {
        Integer userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        UserVO userVO = MappingUtils.beanConvert(user, UserVO.class);
        userVO.setDeptId(user.getDeptid());
        userVO.setRoleId(user.getRoleid());
        Dept dept = departmentMapper.selectById(user.getDeptid());
        Role role = roleMapper.selectById(user.getRoleid());
        userVO.setDeptFullName(dept.getFullname());
        userVO.setRoleName(role.getName());
        return userVO;
    }
}
