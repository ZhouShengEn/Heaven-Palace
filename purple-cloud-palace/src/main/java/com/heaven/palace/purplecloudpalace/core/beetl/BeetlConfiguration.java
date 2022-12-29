package com.heaven.palace.purplecloudpalace.core.beetl;

import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.heaven.palace.purplecloudpalace.core.util.KaptchaUtil;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
        groupTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());

    }

}
