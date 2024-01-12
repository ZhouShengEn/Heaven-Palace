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
import com.heaven.palace.moonpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateFileService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateGroupService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateService;
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

import java.io.UnsupportedEncodingException;

/**
 * 模板管理控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

    private String PREFIX = "/code/template/";

    @Resource
    private ITemplateService templateService;
    @Resource
    private ITemplateFileService templateFileService;
    @Resource
    private ITemplateGroupService templateGroupService;


    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index(Model modelMap) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        return PREFIX + "template.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TemplateAdd(Model modelMap) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        return PREFIX + "template_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TemplateUpdate(@PathVariable Integer id, Model modelMap) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        modelMap.addAttribute("template", templateService.selectById(id));
        modelMap.addAttribute("file", templateFileService.selectOne(new TemplateFileModel(id)));
        return PREFIX + "template_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TemplateModel model) {
        Page<TemplateModel> page = new PageFactory<TemplateModel>().defaultPage();
//        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(templateService.selectPage(page, model, new QueryWrapper<TemplateModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "模板管理新增", key = "companyid")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TemplateModel model, TemplateFileModel fileModel) throws UnsupportedEncodingException {
        fileModel.setFile(hanlderFileEncode(fileModel.getFile()));
        templateService.insert(model, fileModel);
        return SUCCESS_TIP;
    }

    @BussinessLog(value = "模板管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        templateService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "模板管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TemplateModel model, TemplateFileModel fileModel) throws UnsupportedEncodingException {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        fileModel.setFile(hanlderFileEncode(fileModel.getFile()));
        templateService.updateById(model, fileModel);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TemplateModel model) {
        return templateService.selectOne(model);
    }

    private String hanlderFileEncode(String file) {
        return file.replaceAll("& #40;","(")
                .replaceAll("& #41;",")")
                .replaceAll("& lt;","<")
                .replaceAll("& gt;",">")
                .replaceAll("& #39;","'");
    }
}
