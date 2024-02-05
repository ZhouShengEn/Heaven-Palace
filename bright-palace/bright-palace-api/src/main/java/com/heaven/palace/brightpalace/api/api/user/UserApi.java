package com.heaven.palace.brightpalace.api.api.user;

import com.heaven.palace.brightpalace.api.api.user.vo.UserInfoResVO;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.heaven.palace.brightpalace.api.enums.ApiConst.LOAD_BALANCE_SERVICE_NAME;
import static com.heaven.palace.brightpalace.api.enums.ApiConst.USER_API_PREFIX;

/**
 * @author 10733
 * @date 2024/1/14 21:54
 * @description: 用户认证api
 */
@FeignClient(value = LOAD_BALANCE_SERVICE_NAME)
@Api(tags = "用户入口")
public interface UserApi {

    @PostMapping(value = USER_API_PREFIX + "/queryUserInfo")
    @ApiOperation(value = "查询用户信息")
    GlobalRestResponse<UserInfoResVO> queryUserInfo(@RequestParam Long userId);

}
