package com.heaven.palace.heavensouthgate.util;

import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @Author: zhoushengen
 * @Description: 网关工具类
 * @DateTime: 2024/1/10 10:29
 **/
public class GatewayUtil {

    /**
     * 获取认证token
     * @param request
     * @return
     */
    public static String obtainAuthorization(ServerHttpRequest request) {
        String authorization;
        if (null == request || request.getHeaders().isEmpty()
            || StringUtils.isEmpty(authorization = request.getHeaders().getFirst(CommonConst.Header.AUTH_HEADER))
            || !authorization.startsWith(CommonConst.Header.AUTH_HEADER_BEARER.concat(" "))) {
            return null;
        }
        return authorization.replaceFirst(CommonConst.Header.AUTH_HEADER_BEARER.concat(" "), "");
    }
}
