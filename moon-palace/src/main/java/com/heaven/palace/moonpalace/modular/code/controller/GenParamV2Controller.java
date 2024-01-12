package com.heaven.palace.moonpalace.modular.code.controller;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.code.model.GenParamModel;
import com.heaven.palace.moonpalace.modular.code.service.IGenParamService;
import com.heaven.palace.moonpalace.modular.code.vo.GenParamPageReqVO;
import com.heaven.palace.moonpalace.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "参数管理")
@RequestMapping("/gen/param")
public class GenParamV2Controller {

    @Resource
    private IGenParamService genParamService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询")
    public PageResultResponse<GenParamModel> page(@RequestBody GenParamPageReqVO model) {
        com.github.pagehelper.Page<Object> result = PageHelper.startPage(model.getPage(), model.getLimit(),
            "id desc");
        return new PageResultResponse<>(result.getTotal(), genParamService.page(model));
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询列表")
    public ObjectRestResponse<List<GenParamModel>> list() {
        GenParamModel genParamModel = new GenParamModel();
        genParamModel.setUserId(ShiroKit.getUser().getId());
        return new ObjectRestResponse<>().data(genParamService.selectList(genParamModel));
    }


    @BussinessLog(value = "生成参数新增", key = "codePackage")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @Permission
    @ApiOperation(value = "生成参数新增")
    public ObjectRestResponse<Void> add(@RequestBody GenParamModel model) {
        genParamService.insert(model);
        return new ObjectRestResponse<>().message("新增成功！");
    }


    @BussinessLog(value = "生成参数删除", key = "id")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    // @Permission
    @ApiOperation(value = "生成参数删除")
    public ObjectRestResponse<Void> delete(@RequestParam Integer id) {
        genParamService.deleteById(id);
        return new ObjectRestResponse<>().message("删除成功！");
    }


    @BussinessLog(value = "生成参数修改", key = "id")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    // @Permission
    @ApiOperation(value = "生成参数修改")
    public ObjectRestResponse<Void> update(@RequestBody GenParamModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        genParamService.updateById(model);
        return new ObjectRestResponse<>().message("修改成功！");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "详情")
    public ObjectRestResponse<GenParamModel> detail(@RequestParam Integer id) {
        GenParamModel genParamModel = new GenParamModel();
        genParamModel.setId(id);
        return new ObjectRestResponse<>().data(genParamService.selectOne(genParamModel));
    }
}
