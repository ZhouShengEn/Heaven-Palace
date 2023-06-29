package com.heaven.palace.moonpalace.component.shiro.filter;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ShiroAuthenticationFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSONObject.toJSON("登录认证失效，请重新登录!").toString());
        httpServletResponse.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        return false;
    }
}
