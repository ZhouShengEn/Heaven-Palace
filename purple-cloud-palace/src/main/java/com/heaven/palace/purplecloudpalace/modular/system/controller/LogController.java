package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.support.BeanKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.heaven.palace.purplecloudpalace.common.annotion.BussinessLog;
import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.common.constant.Const;
import com.heaven.palace.purplecloudpalace.common.constant.factory.PageFactory;
import com.heaven.palace.purplecloudpalace.common.constant.state.BizLogType;
import com.heaven.palace.purplecloudpalace.common.persistence.dao.OperationLogMapper;
import com.heaven.palace.purplecloudpalace.common.persistence.model.OperationLog;
import com.heaven.palace.purplecloudpalace.modular.system.dao.LogDao;
import com.heaven.palace.purplecloudpalace.modular.system.warpper.LogWarpper;

/**
 * 日志管理的控制器
 *
 * @author ZhouShengEn
 * @Date 2022年8月25日
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private LogDao logDao;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
//    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();
        List<Map<String, Object>> result = logDao.getOperationLogs(page, beginTime, endTime, logName, BizLogType.valueOf(logType), "createtime", false);
        page.setRecords((List<OperationLog>) new LogWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
//    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        OperationLog operationLog = operationLogMapper.selectById(id);
        Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from operation_log");
        return SUCCESS_TIP;
    }
}
