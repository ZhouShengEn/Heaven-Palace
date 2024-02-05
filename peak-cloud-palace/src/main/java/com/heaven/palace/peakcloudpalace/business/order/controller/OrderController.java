package com.heaven.palace.peakcloudpalace.business.order.controller;

import com.heaven.palace.brightpalace.api.api.user.UserApi;
import com.heaven.palace.brightpalace.api.api.user.vo.UserInfoResVO;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zhoushengen
 * @Description: 订单管理控制类
 * @DateTime: 2023/11/22 14:30
 **/
@RestController
@Api(tags = "订单管理")
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Resource
    private UserApi userApi;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询订单列表")
    GlobalRestResponse<String> list() {
        log.info("enter order list interface ！");
        GlobalRestResponse<UserInfoResVO> userInfoResVOGlobalRestResponse = userApi.queryUserInfo(
            CurrentBaseContext.getUserId());
        GlobalRestResponse.checkAndThrow(userInfoResVOGlobalRestResponse);
        return GlobalRestResponse.success("订单返回！");
    }

}
