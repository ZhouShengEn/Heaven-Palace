package com.heaven.palace.moonpalace.modular.custom.element.controller;

import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.DataPreResVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceAddReqVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceDataPreVO;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.ResourceUpdateReqVO;
import com.heaven.palace.moonpalace.modular.custom.element.entity.CustomElement;
import com.heaven.palace.moonpalace.modular.custom.element.manage.CustomElementAuthorityManage;
import com.heaven.palace.moonpalace.modular.custom.element.service.CustomElementService;
import com.heaven.palace.moonpalace.modular.custom.element.vo.CustomElementPageReqVO;
import com.heaven.palace.moonpalace.modular.custom.global.constant.AuthorityConstant;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 10:13
 */
@Slf4j
@RequestMapping(value = "/custom/element")
@RestController
@Api(tags = "接口资源管理")
public class CustomElementController extends BaseController {

    @Resource
    private CustomElementService customElementService;

    @Resource
    private CustomElementAuthorityManage customElementAuthorityManage;


    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询")
    public PageResultResponse<CustomElement> page(@RequestBody CustomElementPageReqVO pageReqVO) {
        return customElementService.page(pageReqVO);
    }


    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增接口")
    public ObjectRestResponse<Void> add(@RequestBody ResourceAddReqVO<CustomElement> customElement) {
        return customElementAuthorityManage.complexAdd(customElement,
            AuthorityConstant.RESOURCE_TYPE.BUTTON.getType(), BusinessCacheEnum.CUSTOM_ELEMENT);
    }


    /**
     * 菜单前置数据准备
     */
    @RequestMapping(value = "/dataPre", method = RequestMethod.POST)
    @ApiOperation(value = "接口前置数据准备")
    public ObjectRestResponse<DataPreResVO<CustomElement>> dataPre(@RequestBody ResourceDataPreVO dataPreVO) {
        return customElementAuthorityManage.dataPre(dataPreVO, AuthorityConstant.RESOURCE_TYPE.BUTTON.getType(), BusinessCacheEnum.CUSTOM_ELEMENT);
    }


    /**
     * 修改菜单
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "修改接口")
    public ObjectRestResponse<Void> update(@RequestBody ResourceUpdateReqVO<CustomElement> customElement) {
        return customElementAuthorityManage.complexUpdate(customElement, AuthorityConstant.RESOURCE_TYPE.BUTTON.getType(), BusinessCacheEnum.CUSTOM_ELEMENT);
    }


    /**
     * 删除菜单
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除接口")
    public ObjectRestResponse<Void> delete(@RequestBody List<Integer> ids) {
        return customElementAuthorityManage.complexBatchDelete(ids, AuthorityConstant.RESOURCE_TYPE.BUTTON, BusinessCacheEnum.CUSTOM_ELEMENT);
    }
}
