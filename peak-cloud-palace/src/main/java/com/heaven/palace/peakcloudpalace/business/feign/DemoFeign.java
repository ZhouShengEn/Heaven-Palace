package com.heaven.palace.peakcloudpalace.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhoushengen
 * @Description: TODO
 * @DateTime: 2023/11/21 17:33
 **/
@FeignClient(name = "demo", path = "/test")
public interface DemoFeign {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    void sayHello();

}
