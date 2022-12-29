package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.constant.Dict;
import com.heaven.palace.purplecloudpalace.common.constant.factory.ConstantFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.common.persistence.dao.NoticeMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.Notice;
import com.heaven.palace.purplecloudpalace.core.log.LogObjectHolder;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.modular.system.dao.NoticeDao;
import com.heaven.palace.purplecloudpalace.modular.system.warpper.NoticeWrapper;

/**
 * 通知控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private String PREFIX = "/system/notice/";

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private NoticeDao noticeDao;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        Notice notice = this.noticeMapper.selectById(noticeId);
        model.addAttribute("notice", notice);
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     */
    @RequestMapping("/hello")
    public String hello() {
        List<Map<String, Object>> notices = noticeDao.list(null);
        super.setAttr("noticeList", notices);
        return "/blackboard.html";
    }

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.noticeDao.list(condition);
        return super.warpObject(new NoticeWrapper(list));
    }

    /**
     * 新增通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知", key = "title", dict = Dict.NoticeMap)
    public Object add(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreater(ShiroKit.getUser().getId());
        notice.setCreatetime(new Date());
        notice.insert();
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知", key = "noticeId", dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer noticeId) {

        //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

        this.noticeMapper.deleteById(noticeId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知", key = "title", dict = Dict.NoticeMap)
    public Object update(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Notice old = this.noticeMapper.selectById(notice.getId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        old.updateById();
        return SUCCESS_TIP;
    }

}
