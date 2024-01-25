package com.heaven.palace.brightpalace.web.api.oauth2;

import com.heaven.palace.brightpalace.api.api.oauth2.Oauth2Api;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.brightpalace.application.factory.auth.MultiOAuth2TypeFactory;
import com.heaven.palace.jasperpalace.base.annotation.IgnoreUserAuth;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zhoushengen
 * @Description: 统一认证api实现
 * @DateTime: 2024/1/24 17:16
 **/
@RestController
public class Oauth2ApiImpl implements Oauth2Api {

    @Resource
    private MultiOAuth2TypeFactory multiOAuth2TypeFactory;

    @Override
    @IgnoreUserAuth
    public GlobalRestResponse<Oauth2QueryTokenResVO> queryToken(Oauth2QueryTokenReqVO queryTokenByCodeReqVO) {
        return GlobalRestResponse.success(multiOAuth2TypeFactory
            .getMultiImplement(queryTokenByCodeReqVO.getResponseType()).queryToken(queryTokenByCodeReqVO));
    }
}
