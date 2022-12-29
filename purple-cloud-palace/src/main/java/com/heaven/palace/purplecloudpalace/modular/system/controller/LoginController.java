package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.google.code.kaptcha.Constants;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.common.constant.state.ManagerStatus;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.InvalidKaptchaException;
import com.heaven.palace.purplecloudpalace.common.persistence.dao.UserMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.User;
import com.heaven.palace.purplecloudpalace.core.log.LogManager;
import com.heaven.palace.purplecloudpalace.core.log.factory.LogTaskFactory;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroUser;
import com.heaven.palace.purplecloudpalace.core.util.ApiMenuFilter;
import com.heaven.palace.purplecloudpalace.core.util.KaptchaUtil;
import com.heaven.palace.purplecloudpalace.modular.system.dao.MenuDao;
import com.heaven.palace.purplecloudpalace.modular.system.dao.UserMgrDao;
import com.heaven.palace.purplecloudpalace.node.MenuNode;
import com.heaven.palace.purplecloudpalace.support.HttpKit;
import com.heaven.palace.purplecloudpalace.util.PatternUtil;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 登录控制器
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    MenuDao menuDao;

    @Resource
    private UserMgrDao managerDao;

    @Autowired
    UserMapper userMapper;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/share.html";
        }
        List<MenuNode> menus = menuDao.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userMapper.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "/register.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerVali(String username, String password) {
        User user = new User();
        user.setAccount(username.trim());
        user.setPassword(password.trim());
        // 判断账号是否重复
        User theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new UnknownAccountException(BizExceptionEnum.USER_ALREADY_REG.getMessage());
        }

        if (!PatternUtil.isPhone(username)) {
            throw new UnknownAccountException("手机号非法");
        }

        // 完善账号信息
        user.setName(username.trim());
        user.setBirthday(new Date());
        user.setSex(1);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        this.userMapper.insert(user);
        this.managerDao.setRoles(user.getId(), "6");

        return REDIRECT + "/login";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
