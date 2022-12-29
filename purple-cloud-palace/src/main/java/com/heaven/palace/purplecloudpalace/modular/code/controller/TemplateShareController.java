package com.heaven.palace.purplecloudpalace.modular.code.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.common.constant.factory.PageFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.modular.code.model.TemplateShareModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITemplateShareService;
import com.heaven.palace.purplecloudpalace.modular.system.warpper.BeanKeyConvert;
import com.heaven.palace.purplecloudpalace.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模板分享管理控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/templateshare")
public class TemplateShareController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateShareController.class);

    private String PREFIX = "/code/share/";

    @Autowired
    private ITemplateShareService templateShareService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "templateshare.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TemplateShareAdd() {
        return PREFIX + "templateshare_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TemplateShareUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("templateshare", templateShareService.selectById(id));
        return PREFIX + "templateshare_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TemplateShareModel model) {
        Page<TemplateShareModel> page = new PageFactory<TemplateShareModel>().defaultPage();
        page.setRecords(templateShareService.selectPage(page, model, new QueryWrapper<TemplateShareModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "模板分享管理新增", key = "companyid")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TemplateShareModel model) {
        templateShareService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "模板分享管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        templateShareService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "模板分享管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TemplateShareModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        templateShareService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TemplateShareModel model) {
        return templateShareService.selectOne(model);
    }
}
