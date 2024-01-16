package com.heaven.palace.brightpalace.api.api.user;

import com.heaven.palace.brightpalace.api.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalace.api.enums.ApiConst;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

/**
 * @author 10733
 * @date 2024/1/14 21:54
 * @description: 用户认证api
 */
@FeignClient(name = ApiConst.USER_API_PREFIX)
@Api(tags = "用户入口")
public interface UserApi {

    @PostMapping(value = "/auth")
    @ApiOperation(value = "用户认证")
    GlobalRestResponse<UserAuthDTO> auth(HttpRequestHandlerServlet request);

}
