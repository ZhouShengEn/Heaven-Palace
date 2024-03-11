package com.heaven.palace.brightpalace.application.factory.auth;

import com.heaven.palace.brightpalace.application.factory.auth.context.Oauth2TypeRegisterConst;
import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.factory.AbstractMultiFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证多类型授权工厂类
 * @DateTime: 2024/1/23 17:03
 **/
@Component
public class MultiOAuth2TypeFactory extends AbstractMultiFactory<Oauth2ApplicationService> {

    @Resource
    private List<Oauth2ApplicationService> multiInterfaces;

    @Override
    public List<Oauth2ApplicationService> getMultiInterfaces() {
        return multiInterfaces;
    }

    public Oauth2ApplicationService getMultiImplement(String multiIdentity) {
        Oauth2ApplicationService oauth2ApplicationService = multiInterfaceMap
                .get(Oauth2TypeRegisterConst.ResponseTypeEnum.markIdentifyByType(multiIdentity));
        if (null == oauth2ApplicationService) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_RESPONSE_TYPE_SERVICE_NULL_ERROR);
        }
        return oauth2ApplicationService;
    }
}
