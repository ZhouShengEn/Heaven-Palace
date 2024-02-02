package com.heaven.palace.brightpalace.api.api.user;

import com.heaven.palace.brightpalace.api.enums.ApiConst;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 10733
 * @date 2024/1/14 21:54
 * @description: 用户认证api
 */
@FeignClient(value = ApiConst.LOAD_BALANCE_SERVICE_NAME)
@Api(tags = "用户入口")
public interface UserApi {

}
