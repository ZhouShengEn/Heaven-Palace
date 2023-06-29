package com.heaven.palace.moonpalace.modular.custom.element.service;

import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.base.service.IService;
import com.heaven.palace.moonpalace.modular.custom.element.entity.CustomElement;
import com.heaven.palace.moonpalace.modular.custom.element.vo.CustomElementPageReqVO;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 10:13
 */
public interface CustomElementService extends IService<CustomElement> {


    /**
     * 分页查询
     */
    PageResultResponse<CustomElement> page(CustomElementPageReqVO pageReqVO);


}
