package com.heaven.palace.purplecloudpalace.modular.code.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.common.constant.factory.PageFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.modular.code.model.TableBaseFieldModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.ITableBaseFieldService;
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
 * 控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/tablebasefield")
public class TableBaseFieldController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableBaseFieldController.class);

    private String PREFIX = "/code/basefield/";

    @Autowired
    private ITableBaseFieldService tableBaseFieldService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tablebasefield.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TableBaseFieldAdd() {
        return PREFIX + "tablebasefield_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TableBaseFieldUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("tablebasefield", tableBaseFieldService.selectById(id));
        return PREFIX + "tablebasefield_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TableBaseFieldModel model) {
        Page<TableBaseFieldModel> page = new PageFactory<TableBaseFieldModel>().defaultPage();
        page.setRecords(tableBaseFieldService.selectPage(page, model, new QueryWrapper<TableBaseFieldModel>()));
        return super.packForBT(page);
    }


    @BussinessLog(value = "新增", key = "companyid")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TableBaseFieldModel model) {
        tableBaseFieldService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        tableBaseFieldService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TableBaseFieldModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        tableBaseFieldService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TableBaseFieldModel model) {
        return tableBaseFieldService.selectOne(model);
    }
}
