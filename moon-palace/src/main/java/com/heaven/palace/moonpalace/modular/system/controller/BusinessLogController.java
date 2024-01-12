package com.heaven.palace.moonpalace.modular.system.controller;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.modular.system.service.BusinessOperationLogService;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageResVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :zhoushengen
 * @date : 2022/9/7
 */
@RestController
@RequestMapping("/business/log")
@Api(tags = "业务日志管理")
public class BusinessLogController {
    @Resource
    BusinessOperationLogService businessOperationLogService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询")
    public PageResultResponse<BusinessLogPageResVO> page(@RequestBody BusinessLogPageReqVO logPageReqVO){
        return businessOperationLogService.page(logPageReqVO);
    }

    @RequestMapping(value = "/synchronize", method = RequestMethod.POST)
    @ApiOperation(value = "同步操作")
    public ObjectRestResponse<Void> synchronize(@RequestParam Integer logId, @RequestParam Integer targetDbId) {
        return businessOperationLogService.synchronize(logId, targetDbId);
    }

    @RequestMapping(value = "/synchronize", method = RequestMethod.DELETE)
    @ApiOperation(value = "同步操作删除")
    public ObjectRestResponse<Void> synchronizeDelete(@RequestParam Integer logId) {
        return businessOperationLogService.synchronizeDelete(logId);
    }
}
