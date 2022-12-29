package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.modular.system.dao.NoticeDao;

/**
 * 总览信息
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    NoticeDao noticeDao;

    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        List<Map<String, Object>> notices = noticeDao.list(null);
        model.addAttribute("noticeList", notices);
        return "/blackboard.html";
    }
}
