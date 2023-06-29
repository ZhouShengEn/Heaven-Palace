package com.heaven.palace.moonpalace.modular.code.controller;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateGroupService;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplateGroupPageReqVO;
import com.heaven.palace.moonpalace.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "模板组管理")
@RequestMapping("/gen/templategroup")
public class GenTemplateGroupController {
    @Autowired
    private ITemplateGroupService templateGroupService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "模板组分页查询")
    public PageResultResponse<TemplateGroupModel> page(@RequestBody GenTemplateGroupPageReqVO reqVO) {

        com.github.pagehelper.Page<Object> result = PageHelper.startPage(reqVO.getPage(), reqVO.getLimit(),
            "id desc");
        List<TemplateGroupModel> res = templateGroupService.page(reqVO);
        return new PageResultResponse<>(result.getTotal(), res);
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "模板组全量查询")
    public ObjectRestResponse<TemplateGroupModel> list() {
        return new ObjectRestResponse<>().data(templateGroupService.selectList(new TemplateGroupModel()));
    }


    @BussinessLog(value = "组管理新增", key = "companyid")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @Permission
    @ApiOperation(value = "组管理新增")
    public ObjectRestResponse<Void> add(@RequestBody TemplateGroupModel model) {
        templateGroupService.insert(model);
        return new ObjectRestResponse<>().message("新增成功！");
    }


    @BussinessLog(value = "组管理删除", key = "id")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    // @Permission
    @ApiOperation(value = "组管理删除")
    public ObjectRestResponse<Void> delete(@RequestParam Integer id) {
        templateGroupService.deleteById(id);
        return new ObjectRestResponse<>().message("删除成功！");
    }


    @BussinessLog(value = "组管理修改", key = "id")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    // @Permission
    @ApiOperation(value = "组管理修改")
    public ObjectRestResponse<Void> update(@RequestBody TemplateGroupModel model) {
        if (ToolUtil.isOneEmpty(model.getId())) {
            throw new BusinessException(BusinessExceptionEnum.REQUEST_NULL);
        }
        templateGroupService.updateById(model);
        return new ObjectRestResponse<>().message("修改成功！");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "组详情")
    public ObjectRestResponse<TemplateGroupModel> detail(@RequestParam Integer id) {
        TemplateGroupModel templateGroupModel = new TemplateGroupModel();
        templateGroupModel.setId(id);
        TemplateGroupModel res = templateGroupService.selectOne(templateGroupModel);
        return new ObjectRestResponse<>().data(res);
    }
}
