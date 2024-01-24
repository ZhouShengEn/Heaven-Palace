package com.heaven.palace.brightpalace.api.api.oauth2;

import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.brightpalace.api.enums.ApiConst;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author: zhoushengen
 * @Description: 统一认证api
 * @DateTime: 2024/1/24 16:38
 **/
@FeignClient(value = ApiConst.FEIGN_SERVICE_NAME, path = ApiConst.OAUTH2_API_PREFIX)
@Api(tags = "统一认证api")
public interface Oauth2Api {


    @PostMapping(value = "/queryToken")
    @ApiOperation(value = "获取token")
    GlobalRestResponse<Oauth2QueryTokenResVO> queryToken(@Valid @RequestBody Oauth2QueryTokenReqVO queryTokenByCodeReqVO);

}
