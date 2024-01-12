package com.heaven.palace.moonpalace.modular.code.controller;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.common.annotion.BussinessLog;
import com.heaven.palace.moonpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateFileService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateService;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageReqVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTemplatePageResVO;
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
@Api(tags = "模板管理")
@RequestMapping("/gen/template")
public class GenTemplateController {

    @Resource
    private ITemplateService templateService;

    @Resource
    private ITemplateFileService templateFileService;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "模板分页查询")
    public PageResultResponse<GenTemplatePageResVO> page(@RequestBody GenTemplatePageReqVO template) {
        com.github.pagehelper.Page<Object> result = PageHelper.startPage(template.getPage(), template.getLimit(),
            "id desc");
        List<GenTemplatePageResVO> res = templateService.page(template);
        return new PageResultResponse<>(result.getTotal(), res);
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "模板全量查询")
    public ObjectRestResponse<List<GenTemplatePageResVO>> list(@RequestParam(required = false) String groupId) {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setGroupId(groupId);
        return new ObjectRestResponse<>().data(templateService.selectList(templateModel));
    }


    @BussinessLog(value = "模板管理新增", key = "companyid")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @Permission
    @ApiOperation(value = "模板管理新增")
    public ObjectRestResponse<Void> add(@RequestBody TemplateModel templateModel) {
        templateService.insertTemplate(templateModel, templateModel.getFileModel());
        return new ObjectRestResponse<>().message("新增成功！");
    }

    @BussinessLog(value = "模板管理删除", key = "id")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    // @Permission
    @ApiOperation(value = "模板管理删除")
    public ObjectRestResponse<Void> delete(@RequestParam Integer id) {
        templateService.deleteById(id);
        return new ObjectRestResponse<>().message("删除成功！");
    }


    @BussinessLog(value = "模板管理修改", key = "id")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    // @Permission
    @ApiOperation(value = "模板管理修改")
    public ObjectRestResponse<Void> update(@RequestBody TemplateModel templateModel) {
        templateService.updateTemplate(templateModel, templateModel.getFileModel());
        return new ObjectRestResponse<>().message("修改成功！");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "模板详情")
    public ObjectRestResponse<TemplateModel> detail(@RequestParam Integer id) {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setId(id);
        return new ObjectRestResponse<>().data(templateService.selectOne(templateModel));
    }

    /**
     * 获取模板文件内容
     */
    @RequestMapping(value = "/file/detail", method = RequestMethod.GET)
    @ApiOperation(value = "模板文件详情")
    public ObjectRestResponse<TemplateFileModel> TemplateUpdate(@RequestParam Integer id) {
        return new ObjectRestResponse<>().data(templateFileService.selectOne(new TemplateFileModel(id)));
    }


}
