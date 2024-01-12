package com.heaven.palace.moonpalace.modular.code.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.common.annotion.Permission;
import com.heaven.palace.moonpalace.common.constant.factory.PageFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateGroupService;
import com.heaven.palace.moonpalace.modular.system.warpper.BeanKeyConvert;
import com.heaven.palace.moonpalace.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 组管理控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/templategroup")
public class TemplateGroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateGroupController.class);

    private String PREFIX = "/code/group/";

    @Resource
    private ITemplateGroupService templateGroupService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "templategroup.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TemplateGroupAdd() {
        return PREFIX + "templategroup_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TemplateGroupUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("templategroup", templateGroupService.selectById(id));
        return PREFIX + "templategroup_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TemplateGroupModel model) {
        Page<TemplateGroupModel> page = new PageFactory<TemplateGroupModel>().defaultPage();
        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(templateGroupService.selectPage(page, model, new QueryWrapper<TemplateGroupModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "组管理新增", key = "companyid")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TemplateGroupModel model) {
        templateGroupService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "组管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        templateGroupService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "组管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TemplateGroupModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        templateGroupService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TemplateGroupModel model) {
        return templateGroupService.selectOne(model);
    }
}
