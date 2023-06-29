package com.heaven.palace.moonpalace.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 全局的控制器
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Controller
@RequestMapping("/global")
public class GlobalController {

    /**
     * 跳转到404页面
     *
     * @author ZhouShengEn
     */
    @RequestMapping(path = "/error")
    public String errorPage() {
        return "/404.html";
    }

}
