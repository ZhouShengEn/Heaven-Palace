package com.heaven.palace.moonpalace.modular.code.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.common.annotion.Permission;
import com.heaven.palace.moonpalace.common.constant.GenCoreConstant;
import com.heaven.palace.moonpalace.common.constant.factory.PageFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.model.CodeGenModel;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.code.model.TableInfoModel;
import com.heaven.palace.moonpalace.modular.code.service.ITableConvertServer;
import com.heaven.palace.moonpalace.modular.code.service.ITableInfoService;
import com.heaven.palace.moonpalace.modular.system.service.IDbInfoService;
import com.heaven.palace.moonpalace.modular.system.warpper.BeanKeyConvert;
import com.heaven.palace.moonpalace.util.CodeGenUtil;
import com.heaven.palace.moonpalace.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@Controller
@RequestMapping("/tableinfo")
public class TableInfoController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableInfoController.class);

    private String PREFIX = "/code/tableinfo/";

    @Resource
    private ITableInfoService tableInfoService;
    @Resource
    private IDbInfoService dbInfoService;
    @Resource(name = "dbTableConvertServer")
    private ITableConvertServer dbTableConvertServer;
    @Resource(name = "sqlTableConvertServer")
    private ITableConvertServer sqlTableConvertServer;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tableinfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goto_add")
    public String tableInfoAdd() {
        return PREFIX + "tableinfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goto_update/{id}")
    public String tbleInfoUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("tableinfo", tableInfoService.selectById(id));
        return PREFIX + "tableinfo_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(TableInfoModel model) {
        Page<TableInfoModel> page = new PageFactory<TableInfoModel>().defaultPage();
        page.setRecords(tableInfoService.selectPage(page, model, new QueryWrapper<TableInfoModel>()));
        BeanKeyConvert.systemUserNameConvert(page.getRecords());
        return super.packForBT(page);
    }


    @BussinessLog(value = "新增", key = "tableName")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(@RequestBody TableInfoModel model) {
        tableInfoService.insert(model);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "删除", key = "id")
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(Integer id) {
        tableInfoService.deleteById(id);
        return SUCCESS_TIP;
    }


    @BussinessLog(value = "修改", key = "id")
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(@RequestBody TableInfoModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        tableInfoService.updateById(model);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(TableInfoModel model) {
        return tableInfoService.selectOne(model);
    }

    @RequestMapping("/goto_dbimport")
    public String tableInfoDbImport() {
        return PREFIX + "tableinfo_dbimport.html";
    }


    @BussinessLog(value = "DB导入", key = "tableName")
    @RequestMapping(value = "/dbimport")
    @Permission
    @ResponseBody
    public Object dbImport(DbInfoModel entity, String dbName, String tableName) {
        entity = dbInfoService.selectOne(entity);
        CodeGenModel model = new CodeGenModel();
        model.setDbType(GenCoreConstant.MYSQL);
        model.setTableName(tableName);
        model.setDbName(dbName);
        model.setUrl(entity.getDbUrl());
        model.setPasswd(entity.getDbPassword());
        model.setUsername(entity.getDbUserName());
        GenBeanEntity bean = CodeGenUtil.getTableBean(model);
        dbTableConvertServer.importBean(JSON.toJSONString(bean), ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    @RequestMapping("/goto_sqlimport")
    public String tableInfoSQLImport() {
        return PREFIX + "tableinfo_sqlimport.html";
    }

    @BussinessLog(value = "SQL导入")
    @RequestMapping(value = "/sqlimport")
    @Permission
    @ResponseBody
    public Object sqlImport(String dbType, String sql) {
        //sql = HtmlUtils.htmlUnescape(sql);
        sql =hanlderFileEncode(sql);
        Map<String, String> map = new HashMap<String, String>();
        map.put("dbType", dbType);
        map.put("sql", sql);
        sqlTableConvertServer.importBean(JSON.toJSONString(map), ShiroKit.getUser().getId());
        return SUCCESS_TIP;
    }

    private String hanlderFileEncode(String sql) {
        return sql.replaceAll("& #40;","(")
                .replaceAll("& #41;",")")
                .replaceAll("& lt;","<")
                .replaceAll("& gt;",">")
                .replaceAll("& #39;","'");
    }
}
