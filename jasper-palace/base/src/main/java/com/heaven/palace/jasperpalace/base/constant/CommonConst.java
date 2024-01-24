package com.heaven.palace.jasperpalace.base.constant;

/**
 * @author :zhoushengen
 * @date : 2022/12/29
 * 公共枚举
 */
public interface CommonConst {

    /**
     * 当前线程持有token信息
     */
    String KEY_USER_TOKEN = "currentThreadUserToken";

    /**
     * 当前线程持有客户端ID
     */
    String KEY_CLIENT_ID = "currentThreadClientId";

    /**
     * 当前线程持有组织编码
     */
    String KEY_ORGANIZATION_CODE = "currentThreadOrgCode";

    /**
     * 当前线程持有用户信息
     */
    String KEY_USER_CACHE = "currentThreadUserCache";
    /**
     * 客户端id参数
     */
    String CLIENT_ID = "clientId";
    /**
     * 请求query连接符号
     */
    String REQUEST_QUERY_PARAM_LINK = "&";
    /**
     * 内部访问请求头：日志链路追踪痕迹
     */
    String LOG_TRACE_ID_HEADER = "traceId";

    /**
     * 内部访问请求头：日志链路追踪请求跨度次数
     */
    String LOG_SPAN_ID_HEADER = "spanId";

    /**
     * 请求头枚举
     */
    interface Header {
        /**
         * 请求头：认证信息
         */
        String AUTH_HEADER = "Authorization";

        /**
         * 认证token前缀
         */
        String AUTH_HEADER_BEARER = "Bearer ";

    }

    /**
     * 统一认证类型枚举
     */
    interface Oauth2ResponseType {
        /**
         * 授权码模式
         */
        String CODE = "code";

    }


}
