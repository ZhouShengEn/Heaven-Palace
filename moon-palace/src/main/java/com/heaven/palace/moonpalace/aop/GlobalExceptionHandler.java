package com.heaven.palace.moonpalace.aop;

import com.alibaba.fastjson.JSONObject;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.common.exception.InvalidKaptchaException;
import com.heaven.palace.moonpalace.core.log.LogManager;
import com.heaven.palace.moonpalace.core.log.factory.LogTaskFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.support.HttpKit;
import lombok.SneakyThrows;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 拦截业务异常 v2
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ObjectRestResponse notFount(BusinessException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        log.error("业务异常:", e);
        return new ObjectRestResponse().statusCode(e.getStatusCode()).message(e.getMessage());
    }

    /**
     * 用户未登录
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuth(AuthenticationException e) {
        log.error("用户未登陆：", e);
        return "/login.html";
    }

    /**
     * 账号被冻结
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accountLocked(DisabledAccountException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结", HttpKit.getIp()));
        model.addAttribute("tips", "账号被冻结");
        return "/login.html";
    }

    /**
     * 账号密码错误
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler({CredentialsException.class, IncorrectCredentialsException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ObjectRestResponse credentials(CredentialsException e) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", HttpKit.getIp()));
        return new ObjectRestResponse().statusCode(BusinessExceptionEnum.CREDENTIALS_EXCEPTION_NULL_ERROR.getCode())
            .message(BusinessExceptionEnum.CREDENTIALS_EXCEPTION_NULL_ERROR.getMessage());
    }

    /**
     * 验证码错误
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String credentials(InvalidKaptchaException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", HttpKit.getIp()));
        model.addAttribute("tips", "验证码错误");
        return "/login.html";
    }

    /**
     * 注册相关错误
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String register(UnknownAccountException e, Model model) {
        model.addAttribute("tips", e.getMessage());
        return "/register.html";
    }

    /**
     * 拦截未知的运行时异常
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ObjectRestResponse notFount(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        log.error("运行时异常:", e);
        return new ObjectRestResponse().statusCode(500).message(e.getMessage());
    }

    /**
     * sql异常
     *
     * @author ZhouShengEn
     */
    @ExceptionHandler({BadSqlGrammarException.class, PersistenceException.class, SQLException.class})
    @ResponseBody
    public ObjectRestResponse databaseError(Exception e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        log.error("数据源异常:", e);
        return new ObjectRestResponse().statusCode(BusinessExceptionEnum.SQL_ERROR.getCode()).message(e.getMessage());
    }

    /**
     * session失效的异常拦截
     *
     * @author ZhouShengEn
     * @Date 2017/6/7 21:02
     */
    @SneakyThrows
    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void sessionTimeout(InvalidSessionException e, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONObject.toJSON("登录认证失效，请重新登录!").toString());
        response.setStatus(cn.hutool.http.HttpStatus.HTTP_UNAUTHORIZED);
    }

    /**
     * session异常
     *
     * @author ZhouShengEn
     * @Date 2017/6/7 21:02
     */
    @SneakyThrows
    @ExceptionHandler(UnknownSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void sessionTimeout(UnknownSessionException e, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONObject.toJSON("登录认证失效，请重新登录!").toString());
        response.setStatus(cn.hutool.http.HttpStatus.HTTP_UNAUTHORIZED);
    }

    private void assertAjax(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
        }
    }

}
