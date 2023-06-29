package com.heaven.palace.moonpalace.modular.system.controller;

import com.google.code.kaptcha.Constants;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.common.exception.InvalidKaptchaException;
import com.heaven.palace.moonpalace.core.log.LogManager;
import com.heaven.palace.moonpalace.core.log.factory.LogTaskFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.core.shiro.ShiroUser;
import com.heaven.palace.moonpalace.modular.system.vo.LoginReqVO;
import com.heaven.palace.moonpalace.support.HttpKit;
import com.heaven.palace.moonpalace.util.KaptchaUtil;
import com.heaven.palace.moonpalace.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "v2登录")
@RequestMapping(value = "/v2")
public class LoginV2Controller extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ObjectRestResponse<Void> login2(@RequestBody LoginReqVO loginReqVO) {
        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = loginReqVO.getKaptcha();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginReqVO.getUserName(), loginReqVO.getPassword().toCharArray());

        Boolean remember = loginReqVO.getRemember();
        token.setRememberMe(null != remember ? remember : false);

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return new ObjectRestResponse<>().message("登录成功！");

    }


    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "登出")
    public ObjectRestResponse<Void> logOut2() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
        ShiroKit.getSubject().logout();
        return new ObjectRestResponse<>().message("登出成功！");
    }
}
