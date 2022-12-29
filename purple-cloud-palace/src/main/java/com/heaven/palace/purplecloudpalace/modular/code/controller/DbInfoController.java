package com.heaven.palace.purplecloudpalace.modular.code.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.common.constant.factory.PageFactory;
import com.heaven.palace.purplecloudpalace.common.exception.BizExceptionEnum;
import com.heaven.palace.purplecloudpalace.common.exception.BussinessException;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.model.enmus.DBType;
import com.heaven.palace.purplecloudpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.purplecloudpalace.modular.code.service.IDbInfoService;
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
 * 数据库管理控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/dbinfo")
public class DbInfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbInfoController.class);

    private String PREFIX = "/code/dbinfo/";

    @Autowired
    private IDbInfoService dbInfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dbinfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String DbInfoAdd() {
        return PREFIX + "dbinfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String DbInfoUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("dbinfo", dbInfoService.selectById(id));
        return PREFIX + "dbinfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(DbInfoModel model) {
        Page<DbInfoModel> page = new PageFactory<DbInfoModel>().defaultPage();
//        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(dbInfoService.selectPage(page, model, new QueryWrapper<DbInfoModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public Object queryAll(DbInfoModel model) {
        Page<DbInfoModel> page = new PageFactory<DbInfoModel>().defaultPage();
//        model.setUserId(ShiroKit.getUser().getId());
        page.setRecords(dbInfoService.selectPage(page, model, new QueryWrapper<DbInfoModel>()));
        return super.packForBT(page);
    }


    @BussinessLog(value = "数据库管理新增", key = "alias")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(DbInfoModel model) {
        model.setDbDriver(DBType.getDbTypeByType(model.getDbType()).getDriver());
        dbInfoService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "数据库管理删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        dbInfoService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "数据库管理修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(DbInfoModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.setDbDriver(DBType.getDbTypeByType(model.getDbType()).getDriver());
        dbInfoService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(DbInfoModel model) {
        return dbInfoService.selectOne(model);
    }
}
