package com.heaven.palace.moonpalace.modular.system.controller;

import com.github.pagehelper.PageHelper;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.model.enmus.DBType;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.system.service.IDbInfoService;
import com.heaven.palace.moonpalace.modular.system.vo.DbInfoPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.DbinfoPageResVO;
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
@Api(tags = "数据源管理")
@RequestMapping("/v2/dbinfo")
public class DbInfoV2Controller  extends BaseController {

    @Autowired
    private IDbInfoService dbInfoService;


    /**
     * 获取列表
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "列表查询")
    public PageResultResponse<DbinfoPageResVO> page(@RequestBody DbInfoPageReqVO pageReqVO) {
        com.github.pagehelper.Page<Object> result = PageHelper.startPage(pageReqVO.getPage(), pageReqVO.getLimit(), "id desc");
        List<DbinfoPageResVO> dbInfoModels = dbInfoService.page(pageReqVO);
        return new PageResultResponse<>(result.getTotal(), dbInfoModels);
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "列表全量查询")
    public ObjectRestResponse<List<DbInfoModel>> list(@RequestParam(required = false)Integer category) {
        DbInfoModel dbInfoModel = new DbInfoModel();
        dbInfoModel.setCategory(category);
        return new ObjectRestResponse<>().data(dbInfoService.selectList(dbInfoModel));
    }

    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增数据源")
    public ObjectRestResponse<Void> add(@RequestBody DbInfoModel baseMenu) {
        dbInfoService.insert(baseMenu);
        return new ObjectRestResponse<>().message("新增成功！");
    }


    /**
     * 修改菜单
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "修改数据源")
    public ObjectRestResponse<Void> update(@RequestBody DbInfoModel dbInfoModel) {
        dbInfoModel.setDbDriver(DBType.getDbTypeByType(dbInfoModel.getDbType()).getDriver());
        dbInfoService.updateById(dbInfoModel);
        return new ObjectRestResponse<>().message("修改成功！");
    }


    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除数据源")
    public ObjectRestResponse<Void> delete(@RequestBody List<Integer> ids) {
        return dbInfoService.delete(ids);
    }


}
