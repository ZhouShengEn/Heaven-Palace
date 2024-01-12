package com.heaven.palace.purplecloudpalace.util;

import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhoushengen
 * @Description: 认证工具类
 * @DateTime: 2024/1/10 9:24
 **/
public class AuthUtil {

    /**
     * 获取认证token
     * @param request
     * @return
     */
    public static String obtainAuthorization(HttpServletRequest request) {
        String authorization;
        if (null == request || StringUtils.isEmpty(authorization = request.getHeader(CommonConst.AUTH_HEADER))
            || !authorization.startsWith(CommonConst.AUTH_HEADER_BEARER.concat(" "))) {
            return null;
        }
        return authorization.replaceFirst(CommonConst.AUTH_HEADER_BEARER.concat(" "), "");
    }
}
