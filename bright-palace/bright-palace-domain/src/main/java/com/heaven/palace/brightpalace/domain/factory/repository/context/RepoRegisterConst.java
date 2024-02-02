package com.heaven.palace.brightpalace.domain.factory.repository.context;

/**
 * @Author: zhoushengen
 * @Description: 仓库注册枚举类
 * @DateTime: 2024/1/18 16:38
 **/
public interface RepoRegisterConst {

    /**
     * 仓库注册名后缀
     */
    String REPO_REGISTER_SUFFIX = "Repo";

    String USER = "user" + REPO_REGISTER_SUFFIX;
    String OAUTH2 = "oauth2" + REPO_REGISTER_SUFFIX;
    String PERMISSION = "permission" + REPO_REGISTER_SUFFIX;
}
