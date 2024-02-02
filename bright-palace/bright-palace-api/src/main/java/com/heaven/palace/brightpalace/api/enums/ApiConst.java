package com.heaven.palace.brightpalace.api.enums;

/**
 * @author 10733
 * @date 2024/1/14 21:54
 * @description: api枚举
 */
public interface ApiConst {

    /**
     * 负载均衡服务名称
     */
    String LOAD_BALANCE_SERVICE_NAME = "bright-palace";

    /**
     * http 负载均衡服务请求前缀
     */
    String HTTP_LOAD_BALANCE_SERVICE_PREFIX = "http://" + LOAD_BALANCE_SERVICE_NAME;

    /**
     * 用户相关api
     */
    String USER_API_PREFIX = "/user";

    /**
     * 统一认证相关api
     */
    String OAUTH2_API_PREFIX = "/oauth2";

    /**
     * 用户检查权限
     */
    String USER_CHECK_PERMISSION_PREFIX = USER_API_PREFIX + "/checkPermission";
}
