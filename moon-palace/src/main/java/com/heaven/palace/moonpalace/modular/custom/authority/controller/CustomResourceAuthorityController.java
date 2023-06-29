package com.heaven.palace.moonpalace.modular.custom.authority.controller;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.modular.custom.authority.entity.CustomResourceAuthority;
import com.heaven.palace.moonpalace.modular.custom.authority.service.CustomResourceAuthorityService;
import com.heaven.palace.moonpalace.modular.custom.authority.vo.AuthorityPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CustomResourceAuthorityController
 *
 * @author ZhouShengEn
 * @date 2023-02-03 17:35
 */
@Slf4j
@RestController
@RequestMapping("/custom/authority")
@Api(tags = "基础资源权限")
public class CustomResourceAuthorityController {
    @Autowired
    private CustomResourceAuthorityService customResourceAuthorityService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "分页查询")
    public PageResultResponse<CustomResourceAuthority> page(@RequestBody AuthorityPageReqVO pageReqVO) {
        return customResourceAuthorityService.page(pageReqVO);
    }


    /**
     * 新增权限
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增权限")
    public ObjectRestResponse<Void> add(@RequestBody CustomResourceAuthority customResourceAuthority) {
        return customResourceAuthorityService.add(customResourceAuthority);
    }


    /**
     * 修改权限
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "修改权限")
    public ObjectRestResponse<Void> update(@RequestBody CustomResourceAuthority customResourceAuthority) {
        return customResourceAuthorityService.update(customResourceAuthority);
    }


    /**
     * 删除权限
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限")
    public ObjectRestResponse<Void> delete(@RequestBody List<Integer> ids) {
        return customResourceAuthorityService.batchDelete(ids);
    }
}