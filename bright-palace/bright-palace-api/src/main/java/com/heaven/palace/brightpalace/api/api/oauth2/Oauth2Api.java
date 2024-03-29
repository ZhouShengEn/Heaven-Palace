package com.heaven.palace.brightpalace.api.api.oauth2;

import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.heaven.palace.brightpalace.api.enums.ApiConst.LOAD_BALANCE_SERVICE_NAME;
import static com.heaven.palace.brightpalace.api.enums.ApiConst.OAUTH2_API_PREFIX;

/**
 * @Author: zhoushengen
 * @Description: 统一认证api
 * @DateTime: 2024/1/24 16:38
 **/
@FeignClient(name = LOAD_BALANCE_SERVICE_NAME)
@Api(tags = "统一认证api")
public interface Oauth2Api {


    @PostMapping(value = OAUTH2_API_PREFIX + "/queryToken")
    @ApiOperation(value = "获取token")
    GlobalRestResponse<Oauth2QueryTokenResVO> queryToken(@Valid @RequestBody Oauth2QueryTokenReqVO queryTokenByCodeReqVO);

}
