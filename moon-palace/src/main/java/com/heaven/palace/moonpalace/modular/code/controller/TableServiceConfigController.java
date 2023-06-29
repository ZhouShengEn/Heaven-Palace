package com.heaven.palace.moonpalace.modular.code.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.common.annotion.Permission;
import com.heaven.palace.moonpalace.common.constant.factory.PageFactory;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.code.model.TableServiceConfigModel;
import com.heaven.palace.moonpalace.modular.code.service.ITableServiceConfigService;
import com.heaven.palace.moonpalace.util.ToolUtil;
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
@RequestMapping("/tableserviceconfig")
public class TableServiceConfigController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableServiceConfigController.class);

    private String PREFIX = "/biz/tableserviceconfig/";

    @Autowired
    private ITableServiceConfigService tableServiceConfigService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tableserviceconfig.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String TableServiceConfigAdd() {
        return PREFIX + "tableserviceconfig_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String TableServiceConfigUpdate(@PathVariable Integer id, Model model) {
	model.addAttribute("tableserviceconfig", tableServiceConfigService.selectById(id));
        return PREFIX + "tableserviceconfig_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TableServiceConfigModel model) {
        Page<TableServiceConfigModel> page = new PageFactory<TableServiceConfigModel>().defaultPage();
        page.setRecords(tableServiceConfigService.selectPage(page,model,new QueryWrapper<TableServiceConfigModel>()));
        return super.packForBT(page);
    }


    @BussinessLog(value = "新增", key = "companyid" )
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(TableServiceConfigModel model) {
        tableServiceConfigService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "删除", key = "id" )
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        tableServiceConfigService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "修改", key = "id" )
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(TableServiceConfigModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        tableServiceConfigService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TableServiceConfigModel model) {
        return tableServiceConfigService.selectOne(model);
    }
}
